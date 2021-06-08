package com.ccakir.feature_repository.list.data

import app.cash.turbine.test
import com.ccakir.authentication.AuthManager
import com.ccakir.common.dispatchprovider.DispatcherProvider
import com.ccakir.feature_repository.list.domain.GetRepositoryListStatus
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

@ExperimentalCoroutinesApi
class GetRepositoryListUseCaseImplTest {

    private lateinit var getRepositoryListUseCase: GetRepositoryListUseCaseImpl

    private val httpClient = HttpClient(MockEngine) {
        install(DefaultRequest) {
            host = "api.github.com"
            url.protocol = URLProtocol.HTTPS
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        engine {
            addHandler {
                val url = it.url.fullUrl
                when {
                    url.contains(USERNAME_SUCCESS) -> {
                        val responseHeaders =
                            headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                        respond(RESPONSE, headers = responseHeaders)
                    }
                    url.contains(USERNAME_FAILURE) -> {
                        respondError(HttpStatusCode.NotFound)
                    }
                    else -> respondBadRequest()
                }
            }
        }
    }

    private val dispatcherProvider = object : DispatcherProvider {

        override fun provideIO(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

        override fun provideMain(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }
    }

    private val authManager = object : AuthManager {
        private var localUsername = USERNAME

        override suspend fun getUsername(): String {
            return localUsername
        }

        override suspend fun setUsername(username: String) {
            localUsername = username
        }

        override suspend fun signOut() {
        }

    }

    @Before
    fun setup() {
        getRepositoryListUseCase = GetRepositoryListUseCaseImpl(
            httpClient,
            dispatcherProvider,
            authManager,
            RepositoryNetworkEntityMapper()
        )
    }

    @Test
    fun `first emitted value should be Loading(true)`() = runBlocking {
        val firstStatus = getRepositoryListUseCase.getRepositoryList().first()
        assertEquals(GetRepositoryListStatus.Loading(true), firstStatus)
    }

    @Test
    fun `last emitted value should be Loading(false)`() = runBlocking {
        val lastStatus = getRepositoryListUseCase.getRepositoryList().last()
        assertEquals(GetRepositoryListStatus.Loading(false), lastStatus)
    }

    @ExperimentalTime
    @Test
    fun `when an exception thrown emitted value should be Error`() = runBlocking {
        authManager.setUsername(USERNAME_FAILURE)

        getRepositoryListUseCase.getRepositoryList().test {
            val loadingTrue = expectItem()
            val error = expectItem()
            assertTrue(error is GetRepositoryListStatus.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `after everything went ok emitted value should be Success`() = runBlocking {
        authManager.setUsername(USERNAME_SUCCESS)
        getRepositoryListUseCase.getRepositoryList().test {
            val loadingTrue = expectItem()
            val success = expectItem()
            assertTrue(success is GetRepositoryListStatus.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private const val USERNAME = "cemilcakir"
        private const val USERNAME_SUCCESS = "cemilcakir_SUCCESS"
        private const val USERNAME_FAILURE = "${USERNAME}_FAILURE"
        private val RESPONSE = Json.encodeToString(
            listOf(
                RepositoryNetworkEntity("name", "desc", "Kotlin")
            )
        )
    }
}