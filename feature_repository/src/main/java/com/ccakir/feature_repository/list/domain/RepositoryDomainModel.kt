package com.ccakir.feature_repository.list.domain

import com.ccakir.feature_repository.R
import java.io.Serializable

data class RepositoryDomainModel(
    val name: String = "",
    val description: String = "",
    val languageIcon: Int = R.drawable.ic_generic
) : Serializable
