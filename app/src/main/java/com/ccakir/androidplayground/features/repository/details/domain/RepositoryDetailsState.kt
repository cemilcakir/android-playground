package com.ccakir.androidplayground.features.repository.details.domain

import com.ccakir.androidplayground.arch.UIState

data class RepositoryDetailsState(
    val commits: List<CommitDomainModel> = emptyList(),
    val inProgress: Boolean = false
) : UIState
