package com.ccakir.androidplayground.features.repository.details.domain

import kotlinx.coroutines.flow.Flow

interface IGetCommitsUseCase {

    fun getCommits(repositoryName: String): Flow<GetCommitsStatus>
}