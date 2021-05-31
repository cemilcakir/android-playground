package com.ccakir.feature_repository.details.domain

sealed class GetCommitsStatus {
    data class Loading(val isLoading: Boolean) : GetCommitsStatus()
    data class Error(val message: String) : GetCommitsStatus()
    data class Success(val commits: List<CommitDomainModel>) : GetCommitsStatus()
}
