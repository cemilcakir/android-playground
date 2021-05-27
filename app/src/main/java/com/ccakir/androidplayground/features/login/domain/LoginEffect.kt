package com.ccakir.androidplayground.features.login.domain

sealed class LoginEffect {
    data class ShowToast(val message: String): LoginEffect()
    object NavigateToRepositoryList: LoginEffect()
}
