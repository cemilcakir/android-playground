package com.ccakir.androidplayground.di

import com.ccakir.authentication.AuthManager
import com.ccakir.common.dispatchprovider.DispatcherProvider
import com.ccakir.feature_login.data.LoginUseCaseImpl
import com.ccakir.feature_login.domain.ILoginUseCase
import com.ccakir.feature_profile.domain.ProfileGetUsernameUseCase
import com.ccakir.feature_profile.domain.ProfileSignOutUseCase
import com.ccakir.feature_repository.details.data.GetCommitsUseCaseImpl
import com.ccakir.feature_repository.details.domain.GetCommitsUseCase
import com.ccakir.feature_repository.list.data.GetRepositoryListUseCaseImpl
import com.ccakir.feature_repository.list.data.RepositoryNetworkEntityMapper
import com.ccakir.feature_repository.list.domain.GetRepositoryListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.ktor.client.*

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModuleHilt {

    @Provides
    fun provideLoginUseCase(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        authManager: AuthManager
    ): ILoginUseCase {
        return LoginUseCaseImpl(
            httpClient,
            dispatcherProvider,
            authManager
        )
    }

    @Provides
    fun provideProfileGetUsernameUseCase(authManager: AuthManager): ProfileGetUsernameUseCase {
        return com.ccakir.feature_profile.data.ProfileGetUsernameUseCaseImpl(authManager)
    }

    @Provides
    fun provideProfileSignOutGetUsernameUseCase(authManager: AuthManager): ProfileSignOutUseCase {
        return com.ccakir.feature_profile.data.ProfileSignOutUseCaseImpl(authManager)
    }

    @Provides
    fun provideGetRepositoryListUseCase(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        authManager: AuthManager,
        repositoryNetworkEntityMapper: RepositoryNetworkEntityMapper
    ): GetRepositoryListUseCase {
        return GetRepositoryListUseCaseImpl(
            httpClient,
            dispatcherProvider,
            authManager,
            repositoryNetworkEntityMapper
        )
    }

    @Provides
    fun provideGetCommitsUseCase(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        authManager: AuthManager,
        commitNetworkEntityMapper: com.ccakir.feature_repository.details.data.CommitNetworkEntityMapper
    ): GetCommitsUseCase {
        return GetCommitsUseCaseImpl(
            httpClient,
            dispatcherProvider,
            authManager,
            commitNetworkEntityMapper
        )
    }
}