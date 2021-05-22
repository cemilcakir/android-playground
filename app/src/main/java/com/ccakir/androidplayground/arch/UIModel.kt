package com.ccakir.androidplayground.arch

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface UIModel<State : UIState, Intent : UIEvent> {
    val events: Channel<Intent>
    val state: StateFlow<State>
}