package com.ccakir.androidplayground.di

import com.ccakir.androidplayground.features.login.ui.LoginViewModel
import com.ccakir.androidplayground.features.profile.ui.ProfileViewModel
import com.ccakir.androidplayground.features.repository.details.ui.RepositoryDetailsViewModel
import com.ccakir.androidplayground.features.repository.list.ui.RepositoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }

    viewModel { ProfileViewModel(get(), get()) }

    viewModel { RepositoryListViewModel(get()) }

    viewModel { params ->
        RepositoryDetailsViewModel(params.get(), get())
    }
}