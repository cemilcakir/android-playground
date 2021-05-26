package com.ccakir.androidplayground.features.repository.details.data

import kotlinx.serialization.Serializable

@Serializable
data class CommitNetworkEntity(
    val commit: Commit
)

@Serializable
data class Commit(
    val message: String,
    val author: Author
)

@Serializable
data class Author(
    val name: String,
    val date: String
)