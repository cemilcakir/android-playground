package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.common.DispatcherProviderImpl
import com.ccakir.androidplayground.common.IDispatcherProvider
import com.ccakir.androidplayground.features.login.data.LoginUseCaseImpl
import com.ccakir.androidplayground.features.login.domain.ILoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<IDispatcherProvider> {
        DispatcherProviderImpl()
    }

    factory<ILoginUseCase> {
        LoginUseCaseImpl(get(), get())
    }
}