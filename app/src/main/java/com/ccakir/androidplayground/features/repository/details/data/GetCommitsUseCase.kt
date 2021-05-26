package com.ccakir.androidplayground.features.repository.details.data

import com.ccakir.androidplayground.auth.IAuthManager
import com.ccakir.androidplayground.common.IDispatcherProvider
import com.ccakir.androidplayground.features.repository.details.domain.CommitDomainModel
import com.ccakir.androidplayground.features.repository.details.domain.IGetCommitsUseCase
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext

class GetCommitsUseCase(
    private val networkClient: HttpClient,
    private val dispatcherProvider: IDispatcherProvider,
    private val authManager: IAuthManager,
    private val entityMapper: CommitNetworkEntityMapper
) : IGetCommitsUseCase {

    override suspend fun getCommits(repositoryName: String): List<CommitDomainModel> {
        val username = authManager.getUsername()!!

        val commits: List<CommitNetworkEntity> = withContext(dispatcherProvider.provideIO()) {
            networkClient.get("repos/$username/$repositoryName/commits")
        }

        return commits.map {
            entityMapper.mapFromEntity(it)
        }
    }
}