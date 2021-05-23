package com.ccakir.androidplayground.features.login.data

import com.ccakir.androidplayground.common.IDispatcherProvider
import com.ccakir.androidplayground.features.login.domain.LoginStatus
import com.ccakir.androidplayground.features.login.domain.ILoginUseCase
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.withContext

class LoginUseCaseImpl(
    private val networkClient: HttpClient,
    private val dispatcherProvider: IDispatcherProvider
) : ILoginUseCase {

    override suspend fun login(username: String): LoginStatus {
        return try {
            withContext(dispatcherProvider.provideIO()) {
                networkClient.get<HttpResponse>(username)
            }
            LoginStatus.Success
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound)
                LoginStatus.Error("$username not found")
            else
                LoginStatus.Error("Unexpected error")
        }
    }
}