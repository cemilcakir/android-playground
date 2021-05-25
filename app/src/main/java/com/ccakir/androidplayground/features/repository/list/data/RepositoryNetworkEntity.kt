package com.ccakir.androidplayground.features.repository.list.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryNetworkEntity(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
    @SerialName("language") val language: String
)
