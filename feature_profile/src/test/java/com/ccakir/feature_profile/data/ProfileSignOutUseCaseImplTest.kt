package com.ccakir.feature_profile.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProfileSignOutUseCaseImplTest {

    private lateinit var profileSignOutUseCase: ProfileSignOutUseCaseImpl

    private val authManager = AuthManagerTD()

    @Before
    fun setup() {
        profileSignOutUseCase = ProfileSignOutUseCaseImpl(authManager)
    }

    @Test
    fun `signOut method called username in authManager should be empty`() = runBlocking {
        profileSignOutUseCase.signOut()
        assertEquals("", authManager.getUsername())
    }
}