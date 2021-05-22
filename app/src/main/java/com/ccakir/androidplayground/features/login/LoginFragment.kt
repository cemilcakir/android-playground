package com.ccakir.androidplayground.features.login

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.ccakir.androidplayground.arch.BaseFragment
import com.ccakir.androidplayground.databinding.FragmentLoginBinding
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

        when (state.loginStatus) {
            is LoginStatus.Error -> Toast.makeText(
                requireContext(),
                state.loginStatus.message,
                Toast.LENGTH_SHORT
            ).show()
            is LoginStatus.Success -> Toast.makeText(
                requireContext(),
                "Login success",
                Toast.LENGTH_SHORT
            ).show()
            else -> {
                // do nothing{
            }
        }
    }

}