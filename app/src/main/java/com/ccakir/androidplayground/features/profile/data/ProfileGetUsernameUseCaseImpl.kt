package com.ccakir.androidplayground.features.profile.data

import com.ccakir.androidplayground.auth.AuthManager
import com.ccakir.androidplayground.features.profile.domain.ProfileGetUsernameUseCase

class ProfileGetUsernameUseCaseImpl(private val authManager: AuthManager) :
    ProfileGetUsernameUseCase {

    override suspend fun getUsername(): String {
        return authManager.getUsername() ?: ""
    }
}