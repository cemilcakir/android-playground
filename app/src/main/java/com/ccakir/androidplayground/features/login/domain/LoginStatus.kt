package com.ccakir.androidplayground.features.login.domain

sealed class LoginStatus {
    object Success : LoginStatus()
    data class Error(val message: String) : LoginStatus()
}