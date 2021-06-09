package com.ccakir.feature_repository.details.data

import app.cash.turbine.test
import com.ccakir.feature_repository.details.domain.GetCommitsStatus
import com.ccakir.feature_repository.list.data.fullUrl
import com.ccakir.feature_repository.shared.AuthManagerTD
import com.ccakir.feature_repository.shared.DispatcherProviderTD
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

class GetCommitsUseCaseImplTest {

    private lateinit var getCommitsUseCase: GetCommitsUseCaseImpl

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

    private val authManager = AuthManagerTD()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        getCommitsUseCase = GetCommitsUseCaseImpl(
            httpClient,
            DispatcherProviderTD(),
            authManager,
            CommitNetworkEntityMapper()
        )
    }

    @Test
    fun `first emitted value should be Loading(true)`() = runBlocking {
        val firstStatus = getCommitsUseCase.getCommits(REPOSITORY_NAME).first()
        assertEquals(GetCommitsStatus.Loading(true), firstStatus)
    }

    @Test
    fun `last emitted value should be Loading(false)`() = runBlocking {
        val lastStatus = getCommitsUseCase.getCommits(REPOSITORY_NAME).last()
        assertEquals(GetCommitsStatus.Loading(false), lastStatus)
    }

    @ExperimentalTime
    @Test
    fun `when an exception thrown emitted value should be Error`() = runBlocking {
        authManager.setUsername(USERNAME_FAILURE)

        getCommitsUseCase.getCommits(REPOSITORY_NAME).test {
            val loadingTrue = expectItem()
            val error = expectItem()
            assertTrue(error is GetCommitsStatus.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `after everything went ok emitted value should be Success`() = runBlocking {
        authManager.setUsername(USERNAME_SUCCESS)
        getCommitsUseCase.getCommits(REPOSITORY_NAME).test {
            val loadingTrue = expectItem()
            val success = expectItem()
            assertTrue(success is GetCommitsStatus.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private const val REPOSITORY_NAME = "android-playground"
        private const val USERNAME = "cemilcakir"
        private const val USERNAME_SUCCESS = "cemilcakir_SUCCESS"
        private const val USERNAME_FAILURE = "${USERNAME}_FAILURE"
        private val RESPONSE = Json.encodeToString(
            listOf(
                CommitNetworkEntity(Commit("commit message", Author("name", "2021")))
            )
        )
    }
}
