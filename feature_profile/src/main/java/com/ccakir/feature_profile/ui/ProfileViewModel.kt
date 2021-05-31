package com.ccakir.feature_profile.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.architecture.base.BaseViewModel
import com.ccakir.feature_profile.domain.ProfileEvent
import com.ccakir.feature_profile.domain.ProfileGetUsernameUseCase
import com.ccakir.feature_profile.domain.ProfileSignOutUseCase
import com.ccakir.feature_profile.domain.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileGetUsernameUseCase: ProfileGetUsernameUseCase,
    private val profileSignOutUseCase: ProfileSignOutUseCase
) : BaseViewModel<ProfileState, ProfileEvent>(ProfileState()) {

    init {
        viewModelScope.launch {
            val username = profileGetUsernameUseCase.getUsername()
            setState(
                state.value.copy(username = username)
            )
        }
    }

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SignOut -> viewModelScope.launch { signOut() }
        }
    }

    private suspend fun signOut() {
        profileSignOutUseCase.signOut()
        setState(
            state.value.copy(navigateToLogin = true)
        )
    }
}