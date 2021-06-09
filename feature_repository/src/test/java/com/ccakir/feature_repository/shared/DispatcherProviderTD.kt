package com.ccakir.feature_repository.shared

import com.ccakir.common.dispatchprovider.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class DispatcherProviderTD : DispatcherProvider {

    override fun provideIO(): CoroutineDispatcher {
        return TestCoroutineDispatcher()
    }

    override fun provideMain(): CoroutineDispatcher {
        return TestCoroutineDispatcher()
    }
}