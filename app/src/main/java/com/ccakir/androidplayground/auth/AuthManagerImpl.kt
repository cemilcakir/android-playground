package com.ccakir.androidplayground.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ccakir.androidplayground.common.IDispatcherProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

class AuthManagerImpl(
    private val context: Context,
    private val dispatcherProvider: IDispatcherProvider
) : IAuthManager {

    companion object {
        private val USERNAME = stringPreferencesKey("USERNAME")
    }

    override suspend fun getUsername(): String {
        return withContext(dispatcherProvider.provideIO()) {
            return@withContext context.dataStore.data.map { auth ->
                auth[USERNAME].toString()
            }.first()
        }
    }

    override suspend fun setUsername(username: String) {
        withContext(dispatcherProvider.provideIO()) {
            context.dataStore.edit { auth ->
                auth[USERNAME] = username
            }
        }
    }
}