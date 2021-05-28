package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.common.network.ConnectivityInterceptor
import com.ccakir.androidplayground.common.network.KtorClient
import com.ccakir.androidplayground.common.network.WifiService
import org.koin.dsl.module

val networkModule = module {
    single { WifiService(get()) }
    single { ConnectivityInterceptor(get()) }
    single { KtorClient(get()).client }
}