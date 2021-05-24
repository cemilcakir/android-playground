package com.ccakir.androidplayground.features.profile.data

import com.ccakir.androidplayground.auth.IAuthManager
import com.ccakir.androidplayground.features.profile.domain.IProfileGetUsernameUseCase

class ProfileGetUsernameUseCaseImpl(private val authManager: IAuthManager) :
    IProfileGetUsernameUseCase {

    override suspend fun getUsername(): String {
        return authManager.getUsername() ?: ""
    }
}