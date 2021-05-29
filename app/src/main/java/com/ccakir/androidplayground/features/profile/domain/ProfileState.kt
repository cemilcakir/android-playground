package com.ccakir.androidplayground.features.profile.domain

import com.ccakir.architecture.arch.UIState

data class ProfileState(
    val username: String = "",
    val navigateToLogin: Boolean = false
) : UIState
