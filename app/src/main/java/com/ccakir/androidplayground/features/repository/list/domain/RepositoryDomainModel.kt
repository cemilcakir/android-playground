package com.ccakir.androidplayground.features.repository.list.domain

import com.ccakir.androidplayground.R
import java.io.Serializable

data class RepositoryDomainModel(
    val name: String = "",
    val description: String = "",
    val languageIcon: Int = R.drawable.ic_generic
): Serializable
