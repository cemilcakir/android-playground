package com.ccakir.feature_login.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ccakir.authentication.AuthManager
import com.ccakir.common.dispatchprovider.DispatcherProvider
import com.ccakir.feature_login.domain.LoginUseCase
import com.ccakir.feature_login.domain.LoginStatus
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

@ExperimentalCoroutinesApi
class LoginUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginUseCaseImpl: LoginUseCase

    private val httpClient = HttpClient(MockEngine) {
        install(DefaultRequest) {
            host = "api.github.com"
            url.protocol = URLProtocol.HTTPS
        }

        engine {
            addHandler {
                val url = it.url.fullUrl
                when {
                    url.contains(USERNAME_SUCCESS) -> {
                        respondOk("Ok")
                    }
                    url.contains(USERNAME_NOT_FOUND) -> {
                        respondError(HttpStatusCode.NotFound)
                    }
                    else -> respondBadRequest()
                }
            }
        }
    }

    private val dispatcherProvider = object : DispatcherProvider {
        override fun provideMain(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

        override fun provideIO(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }
    }

    private val authManager = object : AuthManager {
        private var localUsername: String = ""

        override suspend fun getUsername(): String {
            return localUsername
        }

        override suspend fun setUsername(username: String) {
            localUsername = username
        }

        override suspend fun signOut() {
            localUsername = ""
        }

    }

    @Before
    fun setUp() {
        loginUseCaseImpl = LoginUseCaseImpl(httpClient, dispatcherProvider, authManager)
    }

    @Test
    fun `first login status should be loading true`() = runBlocking {
        val firstLoginStatus = loginUseCaseImpl.login(USERNAME).first()
        assertEquals(LoginStatus.Loading(true), firstLoginStatus)
    }

    @Test
    fun `last login status should be loading false`() = runBlocking {
        val lastLoginStatus = loginUseCaseImpl.login(USERNAME).last()
        assertEquals(LoginStatus.Loading(false), lastLoginStatus)
    }

    @Test
    fun `when everything went ok success should be returned`() = runBlocking {
        val loginStatusList = arrayListOf<LoginStatus>()
        loginUseCaseImpl.login(USERNAME_SUCCESS).toList(loginStatusList)

        val isSuccessInTheResponseList =
            loginStatusList.firstOrNull { it is LoginStatus.Success } == LoginStatus.Success

        assertEquals(true, isSuccessInTheResponseList)
    }

    @Test
    fun `when everything went ok username should be saved in the authmanager`() = runBlocking {
        val loginStatusList = arrayListOf<LoginStatus>()
        loginUseCaseImpl.login(USERNAME_SUCCESS).toList(loginStatusList)

        assertEquals(USERNAME_SUCCESS, authManager.getUsername())
    }

    @Test
    fun `when user not found user not found error should be returned`() = runBlocking {
        val loginStatusList = arrayListOf<LoginStatus>()
        loginUseCaseImpl.login(USERNAME_NOT_FOUND).toList(loginStatusList)

        val isErrorInTheResponseList =
            loginStatusList.firstOrNull { it is LoginStatus.Error } == LoginStatus.Error("$USERNAME_NOT_FOUND not found")

        assertEquals(true, isErrorInTheResponseList)
    }

    @Test
    fun `when an unexpected error occurred unexpected error should be returned`() = runBlocking {
        val loginStatusList = arrayListOf<LoginStatus>()
        loginUseCaseImpl.login(USERNAME_BAD_REQUEST).toList(loginStatusList)

        val isErrorInTheResponseList =
            loginStatusList.firstOrNull { it is LoginStatus.Error }

        assertNotNull(isErrorInTheResponseList)
    }

    companion object {
        private const val USERNAME = "cemilcakir"
        private const val USERNAME_SUCCESS = "${USERNAME}_SUCCESS"
        private const val USERNAME_NOT_FOUND = "${USERNAME}_NOT_FOUND"
        private const val USERNAME_BAD_REQUEST = "${USERNAME}_BAD_REQUEST"
    }
}