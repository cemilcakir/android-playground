package com.ccakir.feature_repository.list.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.architecture.base.BaseViewModel
import com.ccakir.feature_repository.list.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    getRepositoryListUseCase: GetRepositoryListUseCase
) : BaseViewModel<RepositoryListState, RepositoryListEvent>(
    RepositoryListState()
) {

    init {
        getRepositoryListUseCase.getRepositoryList().onEach { getRepositoryListStatus ->
            when (getRepositoryListStatus) {
                is GetRepositoryListStatus.Error -> {
                    state.value.effects.send(
                        RepositoryListEffect.ShowToast(
                            getRepositoryListStatus.message
                        )
                    )
                }
                is GetRepositoryListStatus.Loading -> {
                    setState(state.value.copy(inProgress = getRepositoryListStatus.isLoading))
                }
                is GetRepositoryListStatus.Success -> {
                    setState(
                        state.value.copy(repositories = getRepositoryListStatus.repositoryList)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onEvent(event: RepositoryListEvent) {
        when (event) {
            is RepositoryListEvent.RepositoryClick -> onRepositoryClick(event.repository)
        }
    }

    private fun onRepositoryClick(repository: RepositoryDomainModel) {
        viewModelScope.launch {
            state.value.effects.send(
                RepositoryListEffect.NavigateTo(
                    RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment(
                        repository
                    )
                )
            )
        }
    }
}