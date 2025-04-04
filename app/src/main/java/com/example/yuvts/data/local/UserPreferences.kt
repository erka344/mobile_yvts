package com.example.yuvts.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    
    companion object {
        val SELECTED_LANGUAGE = stringPreferencesKey("selected_language")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled")
        val NOTIFICATION_INTERVAL = stringPreferencesKey("notification_interval")
    }

    val selectedLanguage: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[SELECTED_LANGUAGE] ?: "Both"
        }

    val notificationEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_ENABLED] ?: true
        }

    val notificationInterval: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_INTERVAL] ?: "1"
        }

    suspend fun updateSelectedLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_LANGUAGE] = language
        }
    }

    suspend fun updateNotificationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_ENABLED] = enabled
        }
    }

    suspend fun updateNotificationInterval(interval: String) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATION_INTERVAL] = interval
        }
    }
} 