package com.ccakir.androidplayground.features.repository.details.domain

import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel
import com.ccakir.architecture.arch.UIEvent

sealed class RepositoryDetailsEvent : UIEvent {
    data class GetCommits(val repository: RepositoryDomainModel) : RepositoryDetailsEvent()
}