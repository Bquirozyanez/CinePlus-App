package com.example.cineplus.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserDataStore(private val context: Context) {

    companion object {
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
    }

    val userFlow: Flow<UserData> = context.dataStore.data.map { prefs ->
        UserData(
            name = prefs[NAME] ?: "",
            email = prefs[EMAIL] ?: "",
            password = prefs[PASSWORD] ?: ""
        )
    }

    // ✅ Guardar TODO (registro)
    suspend fun saveUser(name: String, email: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[NAME] = name
            prefs[EMAIL] = email
            prefs[PASSWORD] = password
        }
    }

    // ✅ Guardar SOLO login (NO toca el nombre)
    suspend fun saveLogin(email: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL] = email
            prefs[PASSWORD] = password
            // ⚠️ NO tocar NAME
        }
    }
}
