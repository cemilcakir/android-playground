package com.ccakir.androidplayground.features.repository.details.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.base.BaseViewModel
import com.ccakir.androidplayground.features.repository.details.domain.*
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RepositoryDetailsViewModel(
    repository: RepositoryDomainModel,
    getCommitsUseCase: IGetCommitsUseCase
) : BaseViewModel<RepositoryDetailsState, RepositoryDetailsEvent>(
    RepositoryDetailsState()
) {

    init {
        getCommitsUseCase.getCommits(repository.name).onEach { getCommitsStatus ->
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

    override fun onEvent(event: RepositoryDetailsEvent) {
    }
}