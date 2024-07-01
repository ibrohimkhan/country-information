package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.local.CountryLocalDataSource
import com.kodeco.android.countryinfo.repository.remote.CountryRemoteDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

class CountryRepositoryImpl(
    private val remoteDataSource: CountryRemoteDataSource,
    private val localDataSource: CountryLocalDataSource,
) : CountryRepository {

    private val _countries = MutableStateFlow(emptyList<Country>())
    override val countries = _countries.asStateFlow()

    private var favorites = setOf<String>()

    override suspend fun fetchCountries() {
        val countriesResponse = remoteDataSource.fetchCountries()
        _countries.value = emptyList()

        if (countriesResponse.isSuccessful) {
            countriesResponse.body()?.let {
                it.toMutableList()
                    .map { country ->
                        country.copy(isFavorite = favorites.contains(country.commonName))
                    }

                deleteAllCountries()
                saveCountries(it)

                _countries.value = it
            }
        } else {
            throw Throwable("Request failed: ${countriesResponse.message()}")
        }
    }

    override suspend fun saveCountries(countries: List<Country>) = localDataSource
        .addCountry(*countries.toTypedArray())

    override suspend fun deleteAllCountries() = localDataSource
        .deleteAllCountries()

    override suspend fun getCountries() {
        _countries.value = emptyList()

        localDataSource.getAllCountriesFlow()
            .catch {
                throw Throwable("Failed to retrieve countries: ${it.message}")
            }
            .collect { value ->
                _countries.value = value ?: emptyList()
            }
    }

    override suspend fun getFavoriteCountries() {
        _countries.value = emptyList()

        localDataSource.getFavoriteCountries()
            .catch {
                throw Throwable("Failed to retrieve countries: ${it.message}")
            }
            .collect { value ->
                _countries.value = value ?: emptyList()
            }
    }

    override suspend fun updateCountry(country: Country) = localDataSource
        .updateCountry(country)

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
