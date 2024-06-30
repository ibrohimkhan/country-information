package com.kodeco.android.countryinfo.repository.local

import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryLocalDataSource {
    fun getAllCountriesFlow(): Flow<List<Country>?>

    fun getFavoriteCountries(): Flow<List<Country>?>

    suspend fun getCountryBy(name: String): Country?

    suspend fun updateCountry(country: Country)

    suspend fun addCountry(vararg country: Country)

    suspend fun deleteCountry(country: Country)
}
