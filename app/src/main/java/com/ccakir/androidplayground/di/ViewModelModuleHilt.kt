package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.auth.AuthManager
import com.ccakir.androidplayground.common.DispatcherProvider
import com.ccakir.androidplayground.features.login.data.LoginUseCaseImpl
import com.ccakir.androidplayground.features.login.domain.ILoginUseCase
import com.ccakir.androidplayground.features.profile.data.ProfileGetUsernameUseCaseImpl
import com.ccakir.androidplayground.features.profile.data.ProfileSignOutUseCaseImpl
import com.ccakir.androidplayground.features.profile.domain.ProfileGetUsernameUseCase
import com.ccakir.androidplayground.features.profile.domain.ProfileSignOutUseCase
import com.ccakir.androidplayground.features.repository.details.data.CommitNetworkEntityMapper
import com.ccakir.androidplayground.features.repository.details.data.GetCommitsUseCaseImpl
import com.ccakir.androidplayground.features.repository.details.domain.GetCommitsUseCase
import com.ccakir.androidplayground.features.repository.list.data.GetRepositoryListUseCaseImpl
import com.ccakir.androidplayground.features.repository.list.data.RepositoryNetworkEntityMapper
import com.ccakir.androidplayground.features.repository.list.domain.GetRepositoryListUseCase
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
        return LoginUseCaseImpl(httpClient, dispatcherProvider, authManager)
    }

    @Provides
    fun provideProfileGetUsernameUseCase(authManager: AuthManager): ProfileGetUsernameUseCase {
        return ProfileGetUsernameUseCaseImpl(authManager)
    }

    @Provides
    fun provideProfileSignOutGetUsernameUseCase(authManager: AuthManager): ProfileSignOutUseCase {
        return ProfileSignOutUseCaseImpl(authManager)
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
        commitNetworkEntityMapper: CommitNetworkEntityMapper
    ): GetCommitsUseCase {
        return GetCommitsUseCaseImpl(
            httpClient,
            dispatcherProvider,
            authManager,
            commitNetworkEntityMapper
        )
    }
}