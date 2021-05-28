package com.ccakir.androidplayground.features.repository.list.ui

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.ccakir.androidplayground.base.BaseFragment
import com.ccakir.androidplayground.common.navigateTo
import com.ccakir.androidplayground.common.showToast
import com.ccakir.androidplayground.databinding.FragmentRepositoryListBinding
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListEffect
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListEvent
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListState
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryListFragment :
    BaseFragment<FragmentRepositoryListBinding, RepositoryListState, RepositoryListEvent, RepositoryListViewModel>() {

    override val viewModel: RepositoryListViewModel by viewModel()

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