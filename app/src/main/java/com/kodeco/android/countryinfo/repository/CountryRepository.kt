package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    val countries: Flow<List<Country>>

    // Remote datasource
    suspend fun fetchCountries()

    // Local datasource
    suspend fun getCountries()
    suspend fun saveCountries(countries: List<Country>)
    suspend fun deleteAllCountries()

    // Business logic
    fun getCountry(name: String): Country?
    fun favorite(country: Country)
}