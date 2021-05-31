package com.ccakir.feature_repository.details.domain

import kotlinx.coroutines.flow.Flow

interface GetCommitsUseCase {

    fun getCommits(repositoryName: String): Flow<GetCommitsStatus>
}