package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.common.KtorClient
import org.koin.dsl.module

val networkModule = module {
    single { KtorClient.client }
}