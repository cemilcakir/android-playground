package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.auth.AuthManager
import org.koin.dsl.module

val authModule = module {
    single { AuthManager }
}