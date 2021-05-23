package com.ccakir.androidplayground.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.arch.UIEvent
import com.ccakir.androidplayground.arch.UIModel
import com.ccakir.androidplayground.arch.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<State : UIState, Event : UIEvent>(initialState: State) : ViewModel(),
    UIModel<State, Event> {

    override val events: Channel<Event> = Channel(Channel.UNLIMITED)

    private var mutableState = MutableStateFlow(initialState)
    override val state: StateFlow<State>
        get() = mutableState

    init {
        processEvents()
    }

    private fun processEvents() {
        events.consumeAsFlow().onEach { event ->
            onEvent(event)
        }.launchIn(viewModelScope)
    }

    protected abstract fun onEvent(event: Event)

    protected fun setState(newState: State) {
        mutableState.value = newState
    }
}