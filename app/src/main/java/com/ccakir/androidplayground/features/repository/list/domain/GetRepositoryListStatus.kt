package com.ccakir.androidplayground.features.repository.list.domain

sealed class GetRepositoryListStatus {
    data class Loading(val isLoading: Boolean) : GetRepositoryListStatus()
    data class Error(val message: String) : GetRepositoryListStatus()
    data class Success(val repositoryList: List<RepositoryDomainModel>) : GetRepositoryListStatus()
}
