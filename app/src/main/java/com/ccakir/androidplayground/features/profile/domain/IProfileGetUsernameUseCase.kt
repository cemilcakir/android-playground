package com.ccakir.androidplayground.features.profile.domain

interface IProfileGetUsernameUseCase {
    suspend fun getUsername(): String
}