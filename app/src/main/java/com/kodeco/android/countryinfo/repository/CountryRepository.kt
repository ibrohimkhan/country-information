package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun fetchCountries(): Flow<List<Country>>
    suspend fun getCountry(name: String): Country?
}
