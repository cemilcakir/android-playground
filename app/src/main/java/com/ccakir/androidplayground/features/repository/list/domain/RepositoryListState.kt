package com.ccakir.androidplayground.features.repository.list.domain

import com.ccakir.architecture.arch.UIState
import kotlinx.coroutines.channels.Channel

data class RepositoryListState(
    val repositories: List<RepositoryDomainModel> = emptyList(),
    val inProgress: Boolean = false,
    val effects: Channel<RepositoryListEffect> = Channel(Channel.UNLIMITED)
) : UIState