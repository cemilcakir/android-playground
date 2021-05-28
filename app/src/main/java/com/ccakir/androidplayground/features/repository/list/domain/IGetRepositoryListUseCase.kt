package com.ccakir.androidplayground.features.repository.list.domain

import kotlinx.coroutines.flow.Flow

interface IGetRepositoryListUseCase {

    fun getRepositoryList(): Flow<GetRepositoryListStatus>
}