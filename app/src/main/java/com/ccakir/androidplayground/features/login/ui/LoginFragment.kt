package com.ccakir.androidplayground.features.login.ui

import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ccakir.androidplayground.base.BaseFragment
import com.ccakir.androidplayground.common.showToast
import com.ccakir.androidplayground.databinding.FragmentLoginBinding
import com.ccakir.androidplayground.features.login.domain.LoginEvent
import com.ccakir.androidplayground.features.login.domain.LoginState
import com.ccakir.androidplayground.features.login.domain.LoginStatus
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginState, LoginEvent, LoginViewModel>() {

    override val viewModel: LoginViewModel by inject()

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

        state.loginStatus.consumeAsFlow().onEach { loginStatus ->
            when (loginStatus) {
                is LoginStatus.Error -> showToast(loginStatus.message)
                is LoginStatus.Success -> onLoginSuccess()
            }
        }.launchIn(lifecycleScope)
    }

    private fun onLoginSuccess() {
        showToast("Login success")

        findNavController().navigate(
            LoginFragmentDirections
                .actionLoginFragmentToRepositoryListFragment()
        )
    }
}