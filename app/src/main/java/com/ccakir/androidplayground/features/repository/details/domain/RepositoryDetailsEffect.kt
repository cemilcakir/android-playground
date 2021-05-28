package com.ccakir.androidplayground.features.repository.details.domain

sealed class RepositoryDetailsEffect {
    data class ShowToast(val message: String) : RepositoryDetailsEffect()
}