package com.kodeco.android.countryinfo.data.store

import kotlinx.coroutines.flow.Flow

interface CountryPrefs {
    fun getLocalStorageEnabled(): Flow<Boolean>
    fun getFavoritesFeatureEnabled(): Flow<Boolean>
    fun getScreenRotationEnabled(): Flow<Boolean>
    fun getFavoriteCountriesFeatureEnabled(): Flow<Boolean>

    suspend fun toggleLocalStorage()
    suspend fun toggleFavoritesFeature()
    suspend fun toggleScreenRotation()
    suspend fun toggleFavoriteCountriesFeature()
}
