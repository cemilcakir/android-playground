package com.ccakir.feature_profile.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProfileGetUsernameUseCaseImplTest {

    private lateinit var profileGetUsernameUseCase: ProfileGetUsernameUseCaseImpl

    private val authManager = AuthManagerTD()

    @Before
    fun setup() {
        profileGetUsernameUseCase = ProfileGetUsernameUseCaseImpl(authManager)
    }

    @Test
    fun `getUsername method should return the username from authManager`() = runBlocking {
        val username = profileGetUsernameUseCase.getUsername()
        assertEquals(AuthManagerTD.USERNAME, username)
    }

}