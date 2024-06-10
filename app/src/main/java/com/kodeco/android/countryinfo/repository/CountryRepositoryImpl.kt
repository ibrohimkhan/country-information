package com.kodeco.android.countryinfo.repository

import android.util.Log
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.networking.RemoteApiService
import com.kodeco.android.countryinfo.utils.getLocalCountryList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(
    private val apiService: RemoteApiService,
    private var countries: List<Country> = emptyList()
) : CountryRepository {

    override fun fetchCountries(): Flow<List<Country>> = flow {
        try {
            val result = apiService.getAllCountries()
            countries = result
            emit(result)

        } catch (e: Exception) {
            Log.e("CountryRepository", "Error fetching countries: ${e.message}")

            // Emit the local country list if the API call fails
            getLocalCountryList()?.let {
                countries = it
                emit(it)
            }
        }
    }

    override fun getCountry(name: String): Country? =
        countries.firstOrNull { country -> country.name.common == name }
}
