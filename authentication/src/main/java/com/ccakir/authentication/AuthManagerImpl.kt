package com.ccakir.authentication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ccakir.common.dispatchprovider.DispatcherProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AuthManagerImpl(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val dataStore: DataStore<Preferences>
) : AuthManager {

    companion object {
        private val USERNAME = stringPreferencesKey("USERNAME")
    }

    override suspend fun getUsername(): String {
        return withContext(dispatcherProvider.provideIO()) {
            return@withContext dataStore.data.map { auth ->
                auth[USERNAME]
            }.first() ?: ""
        }
    }

    override suspend fun setUsername(username: String) {
        withContext(dispatcherProvider.provideIO()) {
            dataStore.edit { auth ->
                auth[USERNAME] = username
            }
        }
    }

    override suspend fun signOut() {
        withContext(dispatcherProvider.provideIO()) {
            dataStore.edit { auth ->
                auth[USERNAME] = ""
            }
        }
    }
}