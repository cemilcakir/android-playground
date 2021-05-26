package com.ccakir.androidplayground.features.repository.details.ui

import androidx.lifecycle.viewModelScope
import com.ccakir.androidplayground.base.BaseViewModel
import com.ccakir.androidplayground.features.repository.details.domain.IGetCommitsUseCase
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsEvent
import com.ccakir.androidplayground.features.repository.details.domain.RepositoryDetailsState
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel(
    private val repository: RepositoryDomainModel,
    private val getCommitsUseCase: IGetCommitsUseCase
) : BaseViewModel<RepositoryDetailsState, RepositoryDetailsEvent>(
    RepositoryDetailsState()
) {

    init {
        viewModelScope.launch {
            setState(state.value.copy(inProgress = true))

            val commits = getCommitsUseCase.getCommits(repository.name)
            setState(
                state.value.copy(commits = commits, inProgress = false)
            )
        }
    }

    override fun onEvent(event: RepositoryDetailsEvent) {
    }
}