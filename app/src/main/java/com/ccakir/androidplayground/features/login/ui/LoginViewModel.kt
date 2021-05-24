package com.ccakir.androidplayground.features.login.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.base.BaseViewModel
import com.ccakir.androidplayground.features.login.domain.ILoginUseCase
import com.ccakir.androidplayground.features.login.domain.LoginEvent
import com.ccakir.androidplayground.features.login.domain.LoginState
import com.ccakir.androidplayground.features.login.domain.LoginStatus
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: ILoginUseCase) :
    BaseViewModel<LoginState, LoginEvent>(LoginState()) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login()
            }
            is LoginEvent.UsernameEntry -> {
                setState(state.value.copy(username = event.username))
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            setState(state.value.copy(inProgress = true))

            if (state.value.username.isNotEmpty()) {
                val loginStatus = loginUseCase.login(state.value.username)
                state.value.loginStatus.send(loginStatus)
            } else
                state.value.loginStatus.send(
                    LoginStatus.Error("invalid username")
                )

            setState(state.value.copy(inProgress = false))
        }
    }

}