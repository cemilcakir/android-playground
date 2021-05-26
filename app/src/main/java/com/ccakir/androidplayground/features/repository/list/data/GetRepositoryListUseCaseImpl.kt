package com.ccakir.androidplayground.features.repository.list.data

import com.ccakir.androidplayground.auth.IAuthManager
import com.ccakir.androidplayground.common.IDispatcherProvider
import com.ccakir.androidplayground.features.repository.list.domain.IGetRepositoryListUseCase
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.withContext

class GetRepositoryListUseCaseImpl(
    private val networkClient: HttpClient,
    private val dispatcherProvider: IDispatcherProvider,
    private val authManager: IAuthManager,
    private val entityMapper: RepositoryNetworkEntityMapper
) : IGetRepositoryListUseCase {

    override suspend fun getRepositoryList(): List<RepositoryDomainModel> {
        val username = authManager.getUsername()!!

        val repositoryNetworkEntityList = withContext(dispatcherProvider.provideIO()) {
            networkClient.get<List<RepositoryNetworkEntity>>("users/$username/repos")
        }

        return repositoryNetworkEntityList.map {
            entityMapper.mapFromEntity(it)
        }
    }

}