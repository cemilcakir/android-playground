package com.ccakir.androidplayground.features.repository.list.data

import com.ccakir.androidplayground.R
import com.ccakir.androidplayground.common.EntityMapper
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel
import javax.inject.Inject

class RepositoryNetworkEntityMapper @Inject constructor() :
    EntityMapper<RepositoryNetworkEntity, RepositoryDomainModel> {
    override fun mapFromEntity(entity: RepositoryNetworkEntity): RepositoryDomainModel {
        return RepositoryDomainModel(
            name = entity.name,
            description = entity.description ?: "No description",
            languageIcon = when (entity.language) {
                "Kotlin" -> R.drawable.ic_kotlin
                "JavaScript" -> R.drawable.ic_javascript
                "PHP" -> R.drawable.ic_php
                "HTML" -> R.drawable.ic_html
                else -> R.drawable.ic_generic
            }
        )
    }

    override fun mapToEntity(domainModel: RepositoryDomainModel): RepositoryNetworkEntity {
        return RepositoryNetworkEntity(
            name = domainModel.name,
            description = domainModel.description,
            language = ""
        )
    }

}