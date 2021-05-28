package com.ccakir.androidplayground.features.repository.list.domain

import kotlinx.coroutines.flow.Flow

interface GetRepositoryListUseCase {

    fun getRepositoryList(): Flow<GetRepositoryListStatus>
}