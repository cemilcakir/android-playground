package com.ccakir.androidplayground.features.login.domain

import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    fun login(username: String): Flow<LoginStatus>
}