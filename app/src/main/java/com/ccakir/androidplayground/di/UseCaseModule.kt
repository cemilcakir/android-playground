package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.common.DispatcherProviderImpl
import com.ccakir.androidplayground.common.IDispatcherProvider
import com.ccakir.androidplayground.features.login.data.LoginUseCaseImpl
import com.ccakir.androidplayground.features.login.domain.ILoginUseCase
import com.ccakir.androidplayground.features.profile.data.ProfileGetUsernameUseCaseImpl
import com.ccakir.androidplayground.features.profile.data.ProfileSignOutUseCaseImpl
import com.ccakir.androidplayground.features.profile.domain.IProfileGetUsernameUseCase
import com.ccakir.androidplayground.features.profile.domain.IProfileSignOutUseCase
import com.ccakir.androidplayground.features.repository.list.data.GetRepositoryListUseCaseImpl
import com.ccakir.androidplayground.features.repository.list.data.RepositoryNetworkEntityMapper
import com.ccakir.androidplayground.features.repository.list.domain.IGetRepositoryListUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<IDispatcherProvider> {
        DispatcherProviderImpl()
    }

    factory<ILoginUseCase> {
        LoginUseCaseImpl(get(), get(), get())
    }

    factory<IProfileGetUsernameUseCase> {
        ProfileGetUsernameUseCaseImpl(get())
    }

    factory<IProfileSignOutUseCase> {
        ProfileSignOutUseCaseImpl(get())
    }

    factory {
        RepositoryNetworkEntityMapper()
    }

    factory<IGetRepositoryListUseCase> {
        GetRepositoryListUseCaseImpl(get(), get(), get(), get())
    }
}