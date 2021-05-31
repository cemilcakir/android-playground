package com.ccakir.feature_profile.data

import com.ccakir.feature_profile.domain.ProfileSignOutUseCase
import com.ccakir.authentication.AuthManager

class ProfileSignOutUseCaseImpl(private val authManager: AuthManager) : ProfileSignOutUseCase {

    override suspend fun signOut() {
        authManager.signOut()
    }
}