package com.ccakir.androidplayground.features.repository.details.ui

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ccakir.androidplayground.base.BaseFragment
import com.ccakir.androidplayground.common.showToast
import com.ccakir.androidplayground.databinding.FragmentRepositoryDetailsBinding
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsEffect
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsEvent
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsState
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding, RepositoryDetailsState, RepositoryDetailsEvent, RepositoryDetailsViewModel>() {

    private val args: RepositoryDetailsFragmentArgs by navArgs()

    override val viewModel by viewModel<RepositoryDetailsViewModel> { parametersOf(args.repository) }

    private val adapter = CommitAdapter()

    override fun initBinding() {
        binding = FragmentRepositoryDetailsBinding.inflate(layoutInflater)
        binding.rclCommitList.adapter = adapter
    }

    override fun bindEvents() {
    }

    override fun renderState(state: RepositoryDetailsState) {
        adapter.submitList(state.commits)
        binding.progressIndicator.isVisible = state.inProgress

        state.effects.consumeAsFlow().onEach { effect ->
            when (effect) {
                is RepositoryDetailsEffect.ShowToast -> showToast(effect.message)
            }
        }.launchIn(lifecycleScope)
    }


}