package com.ccakir.androidplayground.features.login.domain

sealed class LoginStatus {
    data class Loading(val isLoading: Boolean) : LoginStatus()
    data class Error(val message: String) : LoginStatus()
    object Success : LoginStatus()
}