package com.ccakir.authentication

interface AuthManager {

    suspend fun getUsername(): String?

    suspend fun setUsername(username: String)

    suspend fun signOut()
}