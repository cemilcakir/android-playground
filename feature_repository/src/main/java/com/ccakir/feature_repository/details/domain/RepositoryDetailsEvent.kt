package com.ccakir.feature_repository.details.domain

import com.ccakir.architecture.arch.UIEvent
import com.ccakir.feature_repository.list.domain.RepositoryDomainModel

sealed class RepositoryDetailsEvent : UIEvent {
    data class GetCommits(val repository: RepositoryDomainModel) : RepositoryDetailsEvent()
}