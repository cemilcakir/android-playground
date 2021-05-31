package com.ccakir.feature_profile.domain

import com.ccakir.architecture.arch.UIEvent

sealed class ProfileEvent : UIEvent {
    object SignOut : ProfileEvent()
}
