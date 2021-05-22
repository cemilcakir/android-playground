package com.ccakir.androidplayground.features.login

import com.ccakir.androidplayground.arch.UIState

data class LoginState(
    val username: String = "",
    val loginStatus: LoginStatus = LoginStatus.Init
) : UIState

sealed class LoginStatus {

    object Init : LoginStatus()
    object Success : LoginStatus()
    data class Error(val message: String) : LoginStatus()
}