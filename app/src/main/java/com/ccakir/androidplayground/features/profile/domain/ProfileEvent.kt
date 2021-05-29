package com.ccakir.androidplayground.features.profile.domain

import com.ccakir.architecture.arch.UIEvent

sealed class ProfileEvent : UIEvent {
    object SignOut : ProfileEvent()
}
