package com.ccakir.androidplayground.features.repository.list.domain

import androidx.navigation.NavDirections
import com.ccakir.androidplayground.arch.UIState
import kotlinx.coroutines.channels.Channel

data class RepositoryListState(
    val repositories: List<RepositoryDomainModel> = emptyList(),
    val inProgress: Boolean = false,
    val navigation: Channel<NavDirections> = Channel(Channel.UNLIMITED)
) : UIState