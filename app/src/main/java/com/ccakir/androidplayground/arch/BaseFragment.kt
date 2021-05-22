package com.ccakir.androidplayground.arch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding, State : UIState, Event : UIEvent,
        ViewModel : BaseViewModel<State, Event>> : Fragment() {
    lateinit var binding: Binding
    abstract val viewModel: ViewModel

    abstract fun initBinding()
    abstract fun bindEvents()
    abstract fun renderState(state: State)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEvents()
        viewModel.state.onEach { state -> renderState(state) }.launchIn(lifecycleScope)
    }

    protected fun sendEvents(event: Event) {
        lifecycleScope.launch {
            viewModel.events.send(event)
        }
    }
}