package com.ccakir.androidplayground.features.repository.details.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.base.BaseViewModel
import com.ccakir.androidplayground.features.repository.details.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailsViewModel @Inject constructor(
    private val getCommitsUseCase: GetCommitsUseCase
) : BaseViewModel<RepositoryDetailsState, RepositoryDetailsEvent>(
    RepositoryDetailsState()
) {

    override fun onEvent(event: RepositoryDetailsEvent) {
        when (event) {
            is RepositoryDetailsEvent.GetCommits -> getCommits(event.repository.name)
        }
    }

    private fun getCommits(repository: String) {
        getCommitsUseCase.getCommits(repository).onEach { getCommitsStatus ->
            when (getCommitsStatus) {
                is GetCommitsStatus.Error -> {
                    state.value.effects.send(RepositoryDetailsEffect.ShowToast(getCommitsStatus.message))
                }
                is GetCommitsStatus.Loading -> {
                    setState(state.value.copy(inProgress = getCommitsStatus.isLoading))
                }
                is GetCommitsStatus.Success -> {
                    setState(state.value.copy(commits = getCommitsStatus.commits))
                }
            }
        }.launchIn(viewModelScope)
    }
}