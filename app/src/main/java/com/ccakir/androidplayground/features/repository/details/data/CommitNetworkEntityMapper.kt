package com.ccakir.androidplayground.features.repository.details.data

import com.ccakir.androidplayground.common.EntityMapper
import com.ccakir.androidplayground.features.repository.details.domain.CommitDomainModel
import javax.inject.Inject

class CommitNetworkEntityMapper @Inject constructor() :
    EntityMapper<CommitNetworkEntity, CommitDomainModel> {

    override fun mapFromEntity(entity: CommitNetworkEntity): CommitDomainModel {
        return CommitDomainModel(
            message = entity.commit.message,
            committer = entity.commit.author.name,
            date = entity.commit.author.date
        )
    }

    override fun mapToEntity(domainModel: CommitDomainModel): CommitNetworkEntity {
        return CommitNetworkEntity(
            commit = Commit(
                message = domainModel.message,
                Author(
                    name = domainModel.committer,
                    date = domainModel.date
                )
            )
        )
    }
}