package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    // Remote datasource
    fun fetchCountries(): Flow<List<Country>>

    // Local datasource
    fun getCountries(): Flow<List<Country>>
    fun getFavoriteCountries(): Flow<List<Country>>
    suspend fun saveCountries(countries: List<Country>)
    suspend fun deleteAllCountries()
    suspend fun updateCountry(country: Country)

    // Business logic
    fun getCountry(name: String): Country?
}
