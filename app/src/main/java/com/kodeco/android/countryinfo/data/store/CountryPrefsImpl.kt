package com.kodeco.android.countryinfo.data.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


private const val STORE_NAME = "country_prefs"

class CountryPrefsImpl(context: Context) : CountryPrefs {

    private val Context.dataStore by preferencesDataStore(name = STORE_NAME)
    private val dataStore = context.dataStore

    val isStorageEnabled = booleanPreferencesKey("isStorageEnabled")
    val isFavoritesFeatureEnabled = booleanPreferencesKey("isFavoritesFeatureEnabled")

    override fun getLocalStorageEnabled(): Flow<Boolean> = flow {
        dataStore.data.map {
            it[isStorageEnabled] ?: false
        }
    }

    override fun getFavoritesFeatureEnabled(): Flow<Boolean> = flow {
        dataStore.data.map {
            it[isFavoritesFeatureEnabled] ?: false
        }
    }

    override suspend fun toggleLocalStorage() {
        dataStore.edit { value ->
            val currentValue = value[isStorageEnabled] ?: false
            value[isStorageEnabled] = !currentValue
        }
    }

    override suspend fun toggleFavoritesFeature() {
        dataStore.edit { value ->
            val currentValue = value[isFavoritesFeatureEnabled] ?: false
            value[isFavoritesFeatureEnabled] = !currentValue
        }
    }
}
