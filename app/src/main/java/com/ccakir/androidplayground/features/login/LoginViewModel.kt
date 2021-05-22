package com.ccakir.androidplayground.features.login

import com.ccakir.androidplayground.arch.BaseViewModel

class LoginViewModel : BaseViewModel<LoginState, LoginEvent>(LoginState()) {

    override fun onEvent(event: LoginEvent) {

        when (event) {
            is LoginEvent.Login -> {
                // TODO make request
                setState(
                    state.value.copy(loginStatus = LoginStatus.Success)
                )
            }
            is LoginEvent.UsernameEntry -> {
                setState(state.value.copy(username = event.username))
            }
        }
    }
}