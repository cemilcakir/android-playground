package com.ccakir.feature_profile.domain

interface ProfileGetUsernameUseCase {
    suspend fun getUsername(): String
}