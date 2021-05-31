package com.ccakir.feature_repository.list.domain

import com.ccakir.architecture.arch.UIEvent

sealed class RepositoryListEvent : UIEvent {
    data class RepositoryClick(val repository: RepositoryDomainModel) : RepositoryListEvent()
}