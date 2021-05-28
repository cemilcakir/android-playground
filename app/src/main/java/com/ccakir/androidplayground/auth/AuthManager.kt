package com.ccakir.androidplayground.auth

interface AuthManager {

    suspend fun getUsername(): String?

    suspend fun setUsername(username: String)

    suspend fun signOut()
}