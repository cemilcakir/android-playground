package com.ccakir.androidplayground.common

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun provideMain(): CoroutineDispatcher
    fun provideIO(): CoroutineDispatcher
}