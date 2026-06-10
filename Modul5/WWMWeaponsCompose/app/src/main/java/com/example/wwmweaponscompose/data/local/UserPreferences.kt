package com.example.wwmweaponscompose.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {

    companion object {
        val LAST_SYNC = longPreferencesKey("last_sync")
    }

    val lastSync: Flow<Long> = context.dataStore.data.map { preferences ->
        preferences[LAST_SYNC] ?: 0L
    }

    suspend fun saveLastSync(time: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_SYNC] = time
        }
    }
}