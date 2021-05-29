package com.ccakir.androidplayground.di

import android.content.Context
import com.ccakir.authentication.AuthManager
import com.ccakir.authentication.AuthManagerImpl
import com.ccakir.common.dispatchprovider.DispatcherProvider
import com.ccakir.common.dispatchprovider.DispatcherProviderImpl
import com.ccakir.network.ConnectivityInterceptor
import com.ccakir.network.KtorClient
import com.ccakir.network.WifiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

    @Provides
    fun provideAuthManager(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): AuthManager {
        return AuthManagerImpl(context, dispatcherProvider)
    }

    @Provides
    fun provideWifiService(@ApplicationContext context: Context): WifiService {
        return WifiService(context)
    }

    @Provides
    fun provideConnectivityInterceptor(wifiService: WifiService): ConnectivityInterceptor {
        return ConnectivityInterceptor(wifiService)
    }

    @Provides
    fun provideKtorClient(connectivityInterceptor: ConnectivityInterceptor): KtorClient {
        return KtorClient(connectivityInterceptor)
    }

    @Provides
    fun provideHttpClient(ktorClient: KtorClient): HttpClient {
        return ktorClient.client
    }
}