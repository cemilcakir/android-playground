package com.ccakir.androidplayground.features.login.ui

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ccakir.androidplayground.databinding.FragmentLoginBinding
import com.ccakir.androidplayground.features.login.domain.LoginEffect
import com.ccakir.androidplayground.features.login.domain.LoginEvent
import com.ccakir.androidplayground.features.login.domain.LoginState
import com.ccakir.architecture.base.BaseFragment
import com.ccakir.base.extensions.navigateTo
import com.ccakir.base.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginState, LoginEvent, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override fun initBinding() {
        binding = FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun bindEvents() {
        binding.etUsername.doOnTextChanged { text, _, _, _ ->
            sendEvents(LoginEvent.UsernameEntry(text?.toString() ?: ""))
        }

        binding.btnLogin.setOnClickListener {
            sendEvents(LoginEvent.Login)
        }
    }

    override fun renderState(state: LoginState) {
        binding.etUsername.setText(state.username)
        binding.etUsername.setSelection(state.username.length)

        binding.tilUsername.isEnabled = !state.inProgress
        binding.etUsername.isEnabled = !state.inProgress
        binding.btnLogin.isEnabled = !state.inProgress

        binding.progressIndicator.isVisible = state.inProgress

        state.effects.consumeAsFlow().onEach { effect ->
            when (effect) {
                LoginEffect.NavigateToRepositoryList -> onLoginSuccess()
                is LoginEffect.ShowToast -> showToast(effect.message)
            }
        }.launchIn(lifecycleScope)
    }

    private fun onLoginSuccess() {
        showToast("Login success")
        navigateTo(LoginFragmentDirections.actionLoginFragmentToRepositoryListFragment())
    }
}