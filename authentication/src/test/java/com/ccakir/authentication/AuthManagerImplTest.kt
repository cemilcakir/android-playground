package com.ccakir.authentication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ccakir.common.dispatchprovider.DispatcherProvider
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthManagerImplTest {

    private lateinit var authManager: AuthManager

    private val context = mockk<Context>()

    private val dispatcherProviderForTest = object : DispatcherProvider {
        override fun provideMain(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

        override fun provideIO(): CoroutineDispatcher {
            return TestCoroutineDispatcher()
        }

    }

    private val dataStore = object : DataStore<Preferences> {

        private var preferences = mutablePreferencesOf(USERNAME to "")

        fun onSignOut() {
            preferences = mutablePreferencesOf(USERNAME to "")
        }

        override val data: Flow<Preferences>
            get() = flowOf(preferences)

        override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
            preferences = mutablePreferencesOf(USERNAME to USERNAME_VALUE)
            return preferences
        }

    }

    companion object {
        private val USERNAME = stringPreferencesKey("USERNAME")
        private const val USERNAME_VALUE = "new_user"
    }

    @Before
    fun setUp() {
        authManager = AuthManagerImpl(context, dispatcherProviderForTest, dataStore)
    }

    @Test
    fun `default username should be empty`() = runBlockingTest {
        val username = authManager.getUsername()
        assertEquals("", username)
    }

    @Test
    fun `the username in the datastore should be the same one that saved`() = runBlockingTest {
        authManager.setUsername(USERNAME_VALUE)
        assertEquals(USERNAME_VALUE, authManager.getUsername())
    }

    @Test
    fun `the username should be empty after signing out`() = runBlockingTest {
        authManager.signOut()
        dataStore.onSignOut()

        assertEquals("", authManager.getUsername())
    }
}