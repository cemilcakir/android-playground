package com.ccakir.androidplayground.features.profile.domain

import com.ccakir.androidplayground.arch.UIEvent

sealed class ProfileEvent : UIEvent {
    object SignOut : ProfileEvent()
}
