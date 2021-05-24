package com.ccakir.androidplayground.features.profile.data

import com.ccakir.androidplayground.auth.IAuthManager
import com.ccakir.androidplayground.features.profile.domain.IProfileSignOutUseCase

class ProfileSignOutUseCaseImpl(private val authManager: IAuthManager) : IProfileSignOutUseCase {

    override suspend fun signOut() {
        authManager.signOut()
    }
}