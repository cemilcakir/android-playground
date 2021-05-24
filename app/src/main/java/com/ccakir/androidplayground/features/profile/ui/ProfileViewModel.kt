package com.ccakir.androidplayground.features.profile.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.base.BaseViewModel
import com.ccakir.androidplayground.features.profile.domain.IProfileGetUsernameUseCase
import com.ccakir.androidplayground.features.profile.domain.IProfileSignOutUseCase
import com.ccakir.androidplayground.features.profile.domain.ProfileEvent
import com.ccakir.androidplayground.features.profile.domain.ProfileState
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileGetUsernameUseCase: IProfileGetUsernameUseCase,
    private val profileSignOutUseCase: IProfileSignOutUseCase
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