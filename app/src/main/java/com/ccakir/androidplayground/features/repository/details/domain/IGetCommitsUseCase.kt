package com.ccakir.androidplayground.features.repository.details.domain

interface IGetCommitsUseCase {

    suspend fun getCommits(repositoryName: String): List<CommitDomainModel>
}