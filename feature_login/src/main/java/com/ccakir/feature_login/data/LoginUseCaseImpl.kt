package com.ccakir.feature_login.data

import com.ccakir.feature_login.domain.LoginUseCase
import com.ccakir.feature_login.domain.LoginStatus
import com.ccakir.authentication.AuthManager
import com.ccakir.common.dispatchprovider.DispatcherProvider
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class LoginUseCaseImpl(
    private val networkClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val authManager: AuthManager
) : LoginUseCase {

    override fun login(username: String) = flow {
        emit(LoginStatus.Loading(true))

        try {
            withContext(dispatcherProvider.provideIO()) {
                networkClient.get<HttpResponse>("users/$username")
                authManager.setUsername(username)
            }
            emit(LoginStatus.Success)
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound)
                emit(LoginStatus.Error("$username not found"))
            else
                emit(LoginStatus.Error("Unexpected error ${e.message}"))
        } catch (e: Exception) {
            emit(LoginStatus.Error("Unexpected error ${e.message}"))
        } finally {
            emit(LoginStatus.Loading(false))
        }
    }
}