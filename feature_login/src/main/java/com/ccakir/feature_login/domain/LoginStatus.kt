package com.ccakir.feature_login.domain

sealed class LoginStatus {
    data class Loading(val isLoading: Boolean) : LoginStatus()
    data class Error(val message: String) : LoginStatus()
    object Success : LoginStatus()
}