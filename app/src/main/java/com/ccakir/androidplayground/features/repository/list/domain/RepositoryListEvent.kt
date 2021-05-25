package com.ccakir.androidplayground.features.repository.list.domain

import com.ccakir.androidplayground.arch.UIEvent

sealed class RepositoryListEvent : UIEvent {
    data class RepositoryClick(val repository: RepositoryDomainModel) : RepositoryListEvent()
}