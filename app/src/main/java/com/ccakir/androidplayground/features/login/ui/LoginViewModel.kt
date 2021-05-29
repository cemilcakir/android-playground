package com.ccakir.androidplayground.features.login.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.features.login.domain.*
import com.ccakir.architecture.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: ILoginUseCase) :
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
        loginUseCase.login(state.value.username).onEach { loginStatus ->
            when (loginStatus) {
                is LoginStatus.Error ->
                    state.value.effects.send(LoginEffect.ShowToast(loginStatus.message))
                is LoginStatus.Loading ->
                    setState(state.value.copy(inProgress = loginStatus.isLoading))
                LoginStatus.Success ->
                    state.value.effects.send(LoginEffect.NavigateToRepositoryList)
            }
        }.launchIn(viewModelScope)
    }
}