package com.ccakir.androidplayground.features.profile.data

import com.ccakir.androidplayground.features.profile.domain.ProfileSignOutUseCase
import com.ccakir.authentication.AuthManager

class ProfileSignOutUseCaseImpl(private val authManager: AuthManager) : ProfileSignOutUseCase {

    override suspend fun signOut() {
        authManager.signOut()
    }
}