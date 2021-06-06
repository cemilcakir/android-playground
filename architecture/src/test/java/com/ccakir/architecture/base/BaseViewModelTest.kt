package com.ccakir.architecture.base

import com.ccakir.architecture.arch.UIEventUnderTest
import com.ccakir.architecture.arch.UIStateUnderTest
import com.ccakir.architecture.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private class ViewModel :
        BaseViewModel<UIStateUnderTest, UIEventUnderTest>(UIStateUnderTest()) {
        override fun onEvent(event: UIEventUnderTest) {
            when (event) {
                is UIEventUnderTest.InitWithData -> setState(
                    state.value.copy(data = event.data)
                )
            }
        }
    }

    private lateinit var viewModel: ViewModel

    @Before
    fun setup() {
        viewModel = ViewModel()
    }

    @Test
    fun `When viewModel initialized Then data should be empty`() {
        assertEquals("", viewModel.state.value.data)
    }

    @Test
    fun `When InitWithData event send Then the data should be the one passed with the event`() =
        runBlockingTest {
            val data = "TEST_DATA"
            viewModel.events.send(UIEventUnderTest.InitWithData(data))

            assertEquals(viewModel.state.value.data, data)
        }
}