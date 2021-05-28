package com.ccakir.androidplayground.features.profile.domain

interface ProfileGetUsernameUseCase {
    suspend fun getUsername(): String
}