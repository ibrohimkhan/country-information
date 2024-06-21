package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    val countries: Flow<List<Country>>

    suspend fun fetchCountries()
    fun getCountry(name: String): Country?
    fun favorite(country: Country)
}
