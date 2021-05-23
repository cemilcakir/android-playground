package com.ccakir.androidplayground.common

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatcherProvider {
    fun provideMain(): CoroutineDispatcher
    fun provideIO(): CoroutineDispatcher
}