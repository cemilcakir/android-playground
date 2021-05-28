package com.ccakir.androidplayground.features.repository.details.domain

import com.ccakir.androidplayground.arch.UIEvent
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel

sealed class RepositoryDetailsEvent : UIEvent {
    data class GetCommits(val repository: RepositoryDomainModel) : RepositoryDetailsEvent()
}