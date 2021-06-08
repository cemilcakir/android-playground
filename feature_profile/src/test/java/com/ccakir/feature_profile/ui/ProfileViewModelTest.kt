package com.ccakir.feature_profile.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ccakir.feature_profile.data.AuthManagerTD
import com.ccakir.feature_profile.domain.ProfileEvent
import com.ccakir.feature_profile.domain.ProfileGetUsernameUseCase
import com.ccakir.feature_profile.domain.ProfileSignOutUseCase
import com.ccakir.feature_profile.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileViewModelTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ProfileViewModel

    private val getUsernameUseCase = object : ProfileGetUsernameUseCase {
        override suspend fun getUsername(): String {
            return AuthManagerTD.USERNAME
        }
    }

    private val signOutUseCase = object : ProfileSignOutUseCase {
        override suspend fun signOut() {
        }
    }

    @Before
    fun setup() {
        viewModel = ProfileViewModel(getUsernameUseCase, signOutUseCase)
    }

    @Test
    fun `when viewModel initialized username should be fetched from profileGetUsernameUseCase`() =
        runBlocking {
            assertEquals(AuthManagerTD.USERNAME, viewModel.state.value.username)
        }

    @Test
    fun `after SignOut event navigateToLogin value should be true`() =
        runBlocking {
            viewModel.events.send(ProfileEvent.SignOut)
            assertTrue(viewModel.state.value.navigateToLogin)
        }
}