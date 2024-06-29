package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.networking.RemoteApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountryRepositoryImpl(
    private val apiService: RemoteApiService,
) : CountryRepository {

    private val _countries = MutableStateFlow(emptyList<Country>())
    override val countries = _countries.asStateFlow()

    private var favorites = setOf<String>()

    override suspend fun fetchCountries() {
        val countriesResponse = apiService.getAllCountries()
        _countries.value = emptyList()

        _countries.value = try {
            if (countriesResponse.isSuccessful) {
                countriesResponse.body()!!
                    .toMutableList()
                    .map { country ->
                        country.copy(isFavorite = favorites.contains(country.commonName))
                    }
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }
        } catch (e: Exception) {
            throw Throwable("Request failed: ${e.message}")
        }
    }

    override fun getCountry(name: String): Country? =
        _countries.value.firstOrNull { country -> country.commonName == name }

    override fun favorite(country: Country) {
        favorites = if (favorites.contains(country.commonName)) {
            favorites - country.commonName
        } else {
            favorites + country.commonName
        }

        val mutableCountries = _countries.value.toMutableList()
        val index = _countries.value.indexOf(country)

        mutableCountries[index] = mutableCountries[index]
            .copy(isFavorite = favorites.contains(country.commonName))

        _countries.value = mutableCountries.toList()
    }
}
