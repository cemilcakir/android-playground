package com.ccakir.feature_repository.details.domain

sealed class RepositoryDetailsEffect {
    data class ShowToast(val message: String) : RepositoryDetailsEffect()
}