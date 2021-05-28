package com.ccakir.androidplayground.features.repository.list.domain

import androidx.navigation.NavDirections

sealed class RepositoryListEffect {
    data class ShowToast(val message: String) : RepositoryListEffect()
    data class NavigateTo(val direction: NavDirections) : RepositoryListEffect()
}