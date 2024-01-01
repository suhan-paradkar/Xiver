import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsUtil(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("Settings")
        private val themePref = intPreferencesKey("Theme")
    }

    val getTheme: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[themePref] ?: 0
    }

    suspend fun saveTheme(token: Int) {
        context.dataStore.edit { preferences ->
            preferences[themePref] = token
        }
    }
}