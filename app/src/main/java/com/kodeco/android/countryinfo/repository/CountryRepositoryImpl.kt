package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.local.CountryLocalDataSource
import com.kodeco.android.countryinfo.repository.remote.CountryRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountryRepositoryImpl(
    private val remoteDataSource: CountryRemoteDataSource,
    private val localDataSource: CountryLocalDataSource,
): CountryRepository {

    private val _countries = MutableStateFlow(emptyList<Country>())
    override val countries = _countries.asStateFlow()

    private var favorites = setOf<String>()

    override suspend fun fetchCountries() {
        try {
            val countriesResponse = remoteDataSource.fetchCountries()
            _countries.value = emptyList()

            if (countriesResponse.isSuccessful) {
                countriesResponse.body()?.let {
                    it.toMutableList()
                        .map { country ->
                            country.copy(isFavorite = favorites.contains(country.commonName))
                        }

                    _countries.value = it
                }
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }

        } catch (e: Exception) {
            throw Throwable("Request failed: ${e.message}")
        }
    }

    override suspend fun saveCountries(countries: List<Country>) = localDataSource
        .addCountry(*countries.toTypedArray())

    override suspend fun getCountries() = localDataSource.getAllCountriesFlow().collect { value ->
        value?.let { _countries.value = it }
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