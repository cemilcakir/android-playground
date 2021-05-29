package com.ccakir.androidplayground.features.login.domain

import com.ccakir.architecture.arch.UIEvent

sealed class LoginEvent : UIEvent {
    object Login : LoginEvent()
    data class UsernameEntry(val username: String) : LoginEvent()
}