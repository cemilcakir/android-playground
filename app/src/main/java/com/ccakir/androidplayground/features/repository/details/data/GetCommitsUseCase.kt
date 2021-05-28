package com.ccakir.androidplayground.features.repository.details.data

import com.ccakir.androidplayground.auth.IAuthManager
import com.ccakir.androidplayground.common.IDispatcherProvider
import com.ccakir.androidplayground.features.repository.details.domain.GetCommitsStatus
import com.ccakir.androidplayground.features.repository.details.domain.IGetCommitsUseCase
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetCommitsUseCase(
    private val networkClient: HttpClient,
    private val dispatcherProvider: IDispatcherProvider,
    private val authManager: IAuthManager,
    private val entityMapper: CommitNetworkEntityMapper
) : IGetCommitsUseCase {

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