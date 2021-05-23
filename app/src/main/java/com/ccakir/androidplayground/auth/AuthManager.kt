package com.ccakir.androidplayground.auth

object AuthManager {
    private var username: String? = null

    fun getUsername() = username

    fun setUsername(user: String) {
        username = user
    }
}