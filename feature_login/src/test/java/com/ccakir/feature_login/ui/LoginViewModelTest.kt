package com.ccakir.feature_login.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ccakir.feature_login.domain.LoginEffect
import com.ccakir.feature_login.domain.LoginEvent
import com.ccakir.feature_login.domain.LoginStatus
import com.ccakir.feature_login.domain.LoginUseCase
import com.ccakir.feature_login.utils.MainCoroutineRule
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    private val loginUseCase = object : LoginUseCase {
        var emitError = false

        override fun login(username: String) = flow {
            emit(LoginStatus.Loading(true))
            if (emitError) emit(LoginStatus.Error("")) else emit(LoginStatus.Success)
            emit(LoginStatus.Loading(false))
        }
    }


    @Before
    fun setup() {
        viewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `when UsernameEntry event username value should be the one that passed`() = runBlocking {
        viewModel.events.send(LoginEvent.UsernameEntry(USERNAME))
        assertEquals(USERNAME, viewModel.state.value.username)
    }

    @ExperimentalTime
    @Test
    fun `inProgress value should be false after initialization and should be true when Login event start and should be false when Login event finished`() =
        runBlocking {
            viewModel.state.test {
                viewModel.events.send(LoginEvent.Login)

                val inProgressValueInitialization = expectItem().inProgress
                assertFalse(inProgressValueInitialization)

                val inProgressValueWhenEventStarted = expectItem().inProgress
                assertTrue(inProgressValueWhenEventStarted)

                val inProgressValueWhenEventFinished = expectItem().inProgress
                assertFalse(inProgressValueWhenEventFinished)

                cancelAndConsumeRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when Login event went successfully effect value should be NavigateToRepositoryList`() =
        runBlocking {
            viewModel.events.send(LoginEvent.Login)

            viewModel.state.value.effects.consumeAsFlow().test {
                assertEquals(LoginEffect.NavigateToRepositoryList, expectItem())
                cancelAndConsumeRemainingEvents()
            }
        }

    @ExperimentalTime
    @Test
    fun `when Login event went wrong effect value should be ShowToast`() =
        runBlocking {
            loginUseCase.emitError = true
            viewModel.events.send(LoginEvent.Login)

            viewModel.state.value.effects.consumeAsFlow().test {
                assertEquals(LoginEffect.ShowToast(""), expectItem())
                cancelAndConsumeRemainingEvents()
            }
        }

    companion object {
        private const val USERNAME = "cemilcakir"
    }
}