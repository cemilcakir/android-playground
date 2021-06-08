package com.ccakir.feature_repository.list.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ccakir.feature_repository.list.domain.*
import com.ccakir.feature_repository.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class RepositoryListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getRepositoryListUseCase = object : GetRepositoryListUseCase {
        var emitError = false

        override fun getRepositoryList() = flow {
            emit(GetRepositoryListStatus.Loading(true))

            if (emitError)
                emit(GetRepositoryListStatus.Error(""))
            else emit(
                GetRepositoryListStatus.Success(
                    repositoryList
                )
            )

            emit(GetRepositoryListStatus.Loading(false))
        }
    }

    private val viewModel by lazy { RepositoryListViewModel(getRepositoryListUseCase) }

    @ExperimentalTime
    @Test
    fun `when everything went ok repositories should be fetched`() = runBlocking {
        viewModel.state.test {
            assertEquals(repositoryList, expectItem().repositories)
            cancelAndConsumeRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when error occurred ShowToast effect should be sent`() = runBlocking {
        getRepositoryListUseCase.emitError = true
        viewModel.state.value.effects.consumeAsFlow().test {
            assertTrue(expectItem() is RepositoryListEffect.ShowToast)
            cancelAndConsumeRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when RepositoryClick event sent NavigateTo effect should be sent`() = runBlocking {
        viewModel.state.value.effects.consumeAsFlow().test {
            viewModel.events.send(RepositoryListEvent.RepositoryClick(repositoryList.first()))
            val effect = expectItem()
            assertTrue(effect is RepositoryListEffect.NavigateTo)
            cancelAndConsumeRemainingEvents()
        }
    }

    companion object {
        private val repositoryList = listOf(
            RepositoryDomainModel("name", "desc")
        )
    }
}