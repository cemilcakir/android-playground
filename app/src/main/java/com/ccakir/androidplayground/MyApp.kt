package com.ccakir.androidplayground

import androidx.multidex.MultiDexApplication
import com.ccakir.androidplayground.di.authModule
import com.ccakir.androidplayground.di.networkModule
import com.ccakir.androidplayground.di.useCaseModule
import com.ccakir.androidplayground.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(networkModule, viewModelModule, useCaseModule, authModule)
        }
    }
}