package com.ccakir.androidplayground.features.profile.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ccakir.androidplayground.databinding.FragmentProfileBinding
import com.ccakir.androidplayground.features.profile.domain.ProfileEvent
import com.ccakir.androidplayground.features.profile.domain.ProfileState
import com.ccakir.architecture.base.BaseFragment
import com.ccakir.base.extensions.navigateTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileState, ProfileEvent, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun initBinding() {
        binding = FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun bindEvents() {
        binding.btnLogOut.setOnClickListener {
            lifecycleScope.launch {
                viewModel.events.send(ProfileEvent.SignOut)
            }
        }
    }

    override fun renderState(state: ProfileState) {
        binding.txtUsername.text = state.username

        if (state.navigateToLogin)
            navigateTo(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

}