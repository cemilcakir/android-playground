package com.ccakir.androidplayground.auth

interface IAuthManager {

    suspend fun getUsername(): String?

    suspend fun setUsername(username: String)

    suspend fun signOut()
}