package com.ccakir.androidplayground.features.login.domain

import com.ccakir.architecture.arch.UIState
import kotlinx.coroutines.channels.Channel

data class LoginState(
    val username: String = "",
    val inProgress: Boolean = false,
    val effects: Channel<LoginEffect> = Channel(Channel.UNLIMITED)
) : UIState