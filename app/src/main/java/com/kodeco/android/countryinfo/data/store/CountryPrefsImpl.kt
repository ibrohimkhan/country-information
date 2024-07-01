package com.kodeco.android.countryinfo.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val STORE_NAME = "country_prefs"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = STORE_NAME)

object PrefsKeys {
    val STORAGE_KEY = booleanPreferencesKey("storage_key")
    val FAVORITES_KEY = booleanPreferencesKey("favorites_key")
    val ROTATION_KEY = booleanPreferencesKey("rotation_key")
}

class CountryPrefsImpl(private val context: Context) : CountryPrefs {

    override fun getLocalStorageEnabled(): Flow<Boolean> = context.dataStore.data.map {
        it[PrefsKeys.STORAGE_KEY] ?: false
    }

    override fun getFavoritesFeatureEnabled(): Flow<Boolean> = context.dataStore.data.map {
        it[PrefsKeys.FAVORITES_KEY] ?: false
    }

    override fun getScreenRotationEnabled(): Flow<Boolean> = context.dataStore.data.map {
        it[PrefsKeys.ROTATION_KEY] ?: false
    }

    override suspend fun toggleLocalStorage() {
        context.dataStore.edit { preferences ->
            val currentValue = preferences[PrefsKeys.STORAGE_KEY] ?: false
            preferences[PrefsKeys.STORAGE_KEY] = !currentValue
        }
    }

    override suspend fun toggleFavoritesFeature() {
        context.dataStore.edit { preferences ->
            val currentValue = preferences[PrefsKeys.FAVORITES_KEY] ?: false
            preferences[PrefsKeys.FAVORITES_KEY] = !currentValue
        }
    }

    override suspend fun toggleScreenRotation() {
        context.dataStore.edit { preferences ->
            val currentValue = preferences[PrefsKeys.ROTATION_KEY] ?: false
            preferences[PrefsKeys.ROTATION_KEY] = !currentValue
        }
    }
}
