package com.ccakir.common.dispatchprovider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl : DispatcherProvider {
    override fun provideMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun provideIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}