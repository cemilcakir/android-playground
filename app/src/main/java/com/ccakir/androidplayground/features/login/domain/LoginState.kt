package com.ccakir.androidplayground.features.login.domain

import com.ccakir.androidplayground.arch.UIState
import kotlinx.coroutines.channels.Channel

data class LoginState(
    val username: String = "",
    val inProgress: Boolean = false,
    val loginStatus: Channel<LoginStatus> = Channel(Channel.UNLIMITED)
) : UIState