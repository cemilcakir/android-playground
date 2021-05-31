package com.ccakir.feature_profile.data

import com.ccakir.feature_profile.domain.ProfileGetUsernameUseCase
import com.ccakir.authentication.AuthManager

class ProfileGetUsernameUseCaseImpl(private val authManager: AuthManager) :
    ProfileGetUsernameUseCase {

    override suspend fun getUsername(): String {
        return authManager.getUsername() ?: ""
    }
}