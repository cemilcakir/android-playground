package com.ccakir.androidplayground.features.profile.ui

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ccakir.androidplayground.base.BaseFragment
import com.ccakir.androidplayground.databinding.FragmentProfileBinding
import com.ccakir.androidplayground.features.profile.domain.ProfileEvent
import com.ccakir.androidplayground.features.profile.domain.ProfileState
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileState, ProfileEvent, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by inject()

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
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
    }

}