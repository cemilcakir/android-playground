package com.ccakir.feature_profile.domain

import com.ccakir.architecture.arch.UIState

data class ProfileState(
    val username: String = "",
    val navigateToLogin: Boolean = false
) : UIState
