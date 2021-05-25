package com.ccakir.androidplayground.features.repository.list.domain

interface IGetRepositoryListUseCase {

    suspend fun getRepositoryList(): List<RepositoryDomainModel>
}