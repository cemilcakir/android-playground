package com.ccakir.androidplayground.features.login

import com.ccakir.androidplayground.arch.UIEvent

sealed class LoginEvent : UIEvent {

    object Login : LoginEvent()
    data class UsernameEntry(val username: String) : LoginEvent()
}