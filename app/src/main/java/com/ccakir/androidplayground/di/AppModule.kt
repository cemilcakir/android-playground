package com.ccakir.androidplayground.di

import android.content.Context
import com.ccakir.androidplayground.auth.AuthManager
import com.ccakir.androidplayground.auth.AuthManagerImpl
import com.ccakir.androidplayground.common.DispatcherProvider
import com.ccakir.androidplayground.common.DispatcherProviderImpl
import com.ccakir.androidplayground.common.network.KtorClient
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
    fun provideHttpClient(ktorClient: KtorClient): HttpClient {
        return ktorClient.client
    }
}