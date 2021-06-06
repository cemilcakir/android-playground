package com.ccakir.base.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
object BaseAppModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DispatcherProviderImpl()
    }

    @Provides
    fun provideAuthManager(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): AuthManager {
        return AuthManagerImpl(context, dispatcherProvider, context.dataStore)
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