package com.ccakir.feature_login.domain

sealed class LoginEffect {
    data class ShowToast(val message: String): LoginEffect()
    object NavigateToRepositoryList: LoginEffect()
}
