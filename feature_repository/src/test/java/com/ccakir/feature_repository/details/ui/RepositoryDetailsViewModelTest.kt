package com.ccakir.feature_repository.details.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.ccakir.feature_repository.details.domain.*
import com.ccakir.feature_repository.list.domain.RepositoryDomainModel
import com.ccakir.feature_repository.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class RepositoryDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getCommitUseCase = object : GetCommitsUseCase {
        var emitError = false

        override fun getCommits(repositoryName: String) = flow {
            emit(GetCommitsStatus.Loading(true))

            if (emitError)
                emit(GetCommitsStatus.Error(""))
            else emit(
                GetCommitsStatus.Success(commitList)
            )

            emit(GetCommitsStatus.Loading(false))

        }
    }

    private val viewModel by lazy { RepositoryDetailsViewModel(getCommitUseCase) }

    @ExperimentalTime
    @Test
    fun `when GetCommits event sent commits should be fetched`() = runBlocking {
        viewModel.events.send(RepositoryDetailsEvent.GetCommits(repositoryDomainModel))
        viewModel.state.test {
            assertEquals(commitList, expectItem().commits)
            cancelAndConsumeRemainingEvents()
        }
    }

    @ExperimentalTime
    @Test
    fun `when error occurred ShowToast effect should be sent`() = runBlocking {
        getCommitUseCase.emitError = true
        viewModel.state.value.effects.consumeAsFlow().test {
            viewModel.events.send(RepositoryDetailsEvent.GetCommits(repositoryDomainModel))
            Assert.assertTrue(expectItem() is RepositoryDetailsEffect.ShowToast)
            cancelAndConsumeRemainingEvents()
        }
    }

    companion object {
        private val repositoryDomainModel = RepositoryDomainModel(name = "android-playground")
        private val commitList = listOf(
            CommitDomainModel("commit message", "Cemil ÇAKIR", "2021")
        )
    }
}