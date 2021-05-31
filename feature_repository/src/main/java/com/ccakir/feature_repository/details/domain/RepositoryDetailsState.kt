package com.ccakir.feature_repository.details.domain

import com.ccakir.architecture.arch.UIState
import kotlinx.coroutines.channels.Channel

data class RepositoryDetailsState(
    val commits: List<CommitDomainModel> = emptyList(),
    val inProgress: Boolean = false,
    val effects: Channel<RepositoryDetailsEffect> = Channel(Channel.UNLIMITED)
) : UIState
