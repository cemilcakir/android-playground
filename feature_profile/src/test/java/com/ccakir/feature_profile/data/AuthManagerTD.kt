package com.ccakir.feature_profile.data

import com.ccakir.authentication.AuthManager

class AuthManagerTD : AuthManager {
    private var localUsername = USERNAME

    override suspend fun getUsername(): String {
        return localUsername
    }

    override suspend fun setUsername(username: String) {
    }

    override suspend fun signOut() {
        localUsername = ""
    }

    companion object {
        const val USERNAME = "cemilcakir"
    }

}