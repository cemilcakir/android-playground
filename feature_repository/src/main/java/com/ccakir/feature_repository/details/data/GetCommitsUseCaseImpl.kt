package com.ccakir.feature_repository.details.data

import com.ccakir.authentication.AuthManager
import com.ccakir.common.dispatchprovider.DispatcherProvider
import com.ccakir.feature_repository.details.domain.GetCommitsStatus
import com.ccakir.feature_repository.details.domain.GetCommitsUseCase
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetCommitsUseCaseImpl(
    private val networkClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val authManager: AuthManager,
    private val entityMapper: CommitNetworkEntityMapper
) : GetCommitsUseCase {

    override fun getCommits(repositoryName: String) = flow {
        emit(GetCommitsStatus.Loading(true))

        try {
            val username = authManager.getUsername()!!

            val commitNetworkEntityList: List<CommitNetworkEntity> =
                withContext(dispatcherProvider.provideIO()) {
                    networkClient.get("repos/$username/$repositoryName/commits")
                }

            val commits = commitNetworkEntityList.map {
                entityMapper.mapFromEntity(it)
            }

            emit(GetCommitsStatus.Success(commits))
        } catch (e: Exception) {
            emit(GetCommitsStatus.Error("Unexpected error occurred: ${e.message}"))
        } finally {
            emit(GetCommitsStatus.Loading(false))
        }
    }
}