package com.ccakir.feature_repository.shared

import com.ccakir.authentication.AuthManager

class AuthManagerTD : AuthManager {
    private var localUsername = USERNAME

    override suspend fun getUsername(): String {
        return localUsername
    }

    override suspend fun setUsername(username: String) {
        localUsername = username
    }

    override suspend fun signOut() {
    }

    companion object {
        const val USERNAME = "cemilcakir"
    }

}