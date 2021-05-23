package com.ccakir.androidplayground.features.login.domain

interface ILoginUseCase {

    suspend fun login(username: String): LoginStatus
}