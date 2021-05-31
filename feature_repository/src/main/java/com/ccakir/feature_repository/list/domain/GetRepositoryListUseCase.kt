package com.ccakir.feature_repository.list.domain

import kotlinx.coroutines.flow.Flow

interface GetRepositoryListUseCase {

    fun getRepositoryList(): Flow<GetRepositoryListStatus>
}