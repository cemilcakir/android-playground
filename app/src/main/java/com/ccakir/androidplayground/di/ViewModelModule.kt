package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.features.login.ui.LoginViewModel
import com.ccakir.androidplayground.features.profile.ui.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }

    viewModel { ProfileViewModel(get(), get()) }
}