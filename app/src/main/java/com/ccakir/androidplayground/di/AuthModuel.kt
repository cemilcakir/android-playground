package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.auth.AuthManagerImpl
import com.ccakir.androidplayground.auth.IAuthManager
import org.koin.dsl.module

val authModule = module {
    single<IAuthManager> { AuthManagerImpl(get(), get()) }
}