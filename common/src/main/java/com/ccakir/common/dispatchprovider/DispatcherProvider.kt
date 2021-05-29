package com.ccakir.common.dispatchprovider

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun provideMain(): CoroutineDispatcher
    fun provideIO(): CoroutineDispatcher
}