package com.ccakir.androidplayground.features.profile.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.features.profile.domain.ProfileEvent
import com.ccakir.androidplayground.features.profile.domain.ProfileGetUsernameUseCase
import com.ccakir.androidplayground.features.profile.domain.ProfileSignOutUseCase
import com.ccakir.androidplayground.features.profile.domain.ProfileState
import com.ccakir.architecture.base.BaseViewModel
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