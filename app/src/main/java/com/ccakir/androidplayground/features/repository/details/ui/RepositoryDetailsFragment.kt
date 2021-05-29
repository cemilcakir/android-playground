package com.ccakir.androidplayground.features.repository.details.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ccakir.androidplayground.databinding.FragmentRepositoryDetailsBinding
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsEffect
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsEvent
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsState
import com.ccakir.architecture.base.BaseFragment
import com.ccakir.base.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding, RepositoryDetailsState, RepositoryDetailsEvent, RepositoryDetailsViewModel>() {

    private val args: RepositoryDetailsFragmentArgs by navArgs()

    override val viewModel: RepositoryDetailsViewModel by viewModels()

    private val adapter = CommitAdapter()

    override fun initBinding() {
        binding = FragmentRepositoryDetailsBinding.inflate(layoutInflater)
        binding.rclCommitList.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.events.send(RepositoryDetailsEvent.GetCommits(args.repository))
        }
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