package com.ccakir.androidplayground.features.profile.data

import com.ccakir.androidplayground.features.profile.domain.ProfileGetUsernameUseCase
import com.ccakir.authentication.AuthManager

class ProfileGetUsernameUseCaseImpl(private val authManager: AuthManager) :
    ProfileGetUsernameUseCase {

    override suspend fun getUsername(): String {
        return authManager.getUsername() ?: ""
    }
}