package com.ccakir.androidplayground.features.profile.data

import com.ccakir.androidplayground.auth.AuthManager
import com.ccakir.androidplayground.features.profile.domain.ProfileSignOutUseCase

class ProfileSignOutUseCaseImpl(private val authManager: AuthManager) : ProfileSignOutUseCase {

    override suspend fun signOut() {
        authManager.signOut()
    }
}