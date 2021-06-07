package com.ccakir.feature_login.domain

import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    fun login(username: String): Flow<LoginStatus>
}