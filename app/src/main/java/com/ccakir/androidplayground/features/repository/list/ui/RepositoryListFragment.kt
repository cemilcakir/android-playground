package com.ccakir.androidplayground.features.repository.list.ui

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ccakir.androidplayground.databinding.FragmentRepositoryListBinding
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListEffect
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListEvent
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListState
import com.ccakir.architecture.base.BaseFragment
import com.ccakir.base.extensions.navigateTo
import com.ccakir.base.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RepositoryListFragment :
    BaseFragment<FragmentRepositoryListBinding, RepositoryListState, RepositoryListEvent, RepositoryListViewModel>() {

    override val viewModel: RepositoryListViewModel by viewModels()

    private val adapter = RepositoryListAdapter { repository ->
        lifecycleScope.launchWhenCreated {
            viewModel.events.send(
                RepositoryListEvent.RepositoryClick(repository)
            )
        }
    }

    override fun initBinding() {
        binding = FragmentRepositoryListBinding.inflate(layoutInflater)
        binding.rclRepositoryList.adapter = adapter
    }

    override fun bindEvents() {
    }

    override fun renderState(state: RepositoryListState) {
        binding.progressIndicator.isVisible = state.inProgress
        adapter.submitList(state.repositories)

        state.effects.consumeAsFlow().onEach { effect ->
            when (effect) {
                is RepositoryListEffect.NavigateTo -> navigateTo(effect.direction)
                is RepositoryListEffect.ShowToast -> showToast(effect.message)
            }
        }.launchIn(lifecycleScope)
    }

}