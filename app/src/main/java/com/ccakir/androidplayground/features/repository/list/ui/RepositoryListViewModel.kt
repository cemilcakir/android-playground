package com.ccakir.androidplayground.features.repository.list.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.base.BaseViewModel
import com.ccakir.androidplayground.features.repository.list.domain.IGetRepositoryListUseCase
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListEvent
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryListState
import kotlinx.coroutines.launch

class RepositoryListViewModel(
    private val getRepositoryListUseCase: IGetRepositoryListUseCase
) : BaseViewModel<RepositoryListState, RepositoryListEvent>(
    RepositoryListState()
) {

    init {
        viewModelScope.launch {
            setState(
                state.value.copy(inProgress = true)
            )

            val repositoryList = getRepositoryListUseCase.getRepositoryList()
            setState(
                state.value.copy(repositories = repositoryList, inProgress = false)
            )
        }
    }

    override fun onEvent(event: RepositoryListEvent) {
        when (event) {
            is RepositoryListEvent.RepositoryClick -> onRepositoryClick(event.repository)
        }
    }

    private fun onRepositoryClick(repository: RepositoryDomainModel) {
        viewModelScope.launch {
            state.value.navigation.send(
                RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment(
                    repository
                )
            )
        }
    }
}