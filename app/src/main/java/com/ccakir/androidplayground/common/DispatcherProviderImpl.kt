package com.ccakir.androidplayground.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : IDispatcherProvider {
    override fun provideMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun provideIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}