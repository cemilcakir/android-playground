package com.ccakir.feature_repository.list.data

import com.ccakir.authentication.AuthManager
import com.ccakir.common.dispatchprovider.DispatcherProvider
import com.ccakir.feature_repository.list.domain.GetRepositoryListStatus
import com.ccakir.feature_repository.list.domain.GetRepositoryListUseCase
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetRepositoryListUseCaseImpl(
    private val networkClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val authManager: AuthManager,
    private val entityMapper: RepositoryNetworkEntityMapper
) : GetRepositoryListUseCase {

    override fun getRepositoryList() = flow {
        emit(GetRepositoryListStatus.Loading(true))

        try {
            val username = authManager.getUsername()!!

            val repositoryNetworkEntityList = withContext(dispatcherProvider.provideIO()) {
                networkClient.get<List<RepositoryNetworkEntity>>("users/$username/repos")
            }

            val repositoryList = repositoryNetworkEntityList.map {
                entityMapper.mapFromEntity(it)
            }

            emit(GetRepositoryListStatus.Success(repositoryList))
        } catch (e: Exception) {
            emit(GetRepositoryListStatus.Error("Unexpected error occurred ${e.message}"))
        } finally {
            emit(GetRepositoryListStatus.Loading(false))
        }
    }
}