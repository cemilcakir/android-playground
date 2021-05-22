package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.features.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel() }
}