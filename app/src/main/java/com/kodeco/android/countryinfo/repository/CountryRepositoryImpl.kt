package com.kodeco.android.countryinfo.repository

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.local.CountryLocalDataSource
import com.kodeco.android.countryinfo.repository.remote.CountryRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountryRepositoryImpl(
    private val remoteDataSource: CountryRemoteDataSource,
    private val localDataSource: CountryLocalDataSource,
) : CountryRepository {

    private var _countries = mutableListOf<Country>()

    override fun fetchCountries(): Flow<List<Country>> = flow {
        val countriesResponse = remoteDataSource.fetchCountries()
        _countries.clear()

        if (countriesResponse.isSuccessful) {
            countriesResponse.body()?.let {
                deleteAllCountries()
                saveCountries(it)
                _countries.addAll(it)

                emit(it)
            }
        } else {
            throw Throwable("Request failed: ${countriesResponse.message()}")
        }
    }

    override suspend fun saveCountries(countries: List<Country>) = localDataSource
        .addCountry(*countries.toTypedArray())

    override suspend fun deleteAllCountries() = localDataSource
        .deleteAllCountries()

    override fun getCountries(): Flow<List<Country>> = flow {
        localDataSource.getAllCountriesFlow().collect {
            _countries.clear()
            _countries.addAll(it)
            emit(it)
        }
    }

    override fun getFavoriteCountries(): Flow<List<Country>> = flow {
        localDataSource.getFavoriteCountriesFlow().collect {
            _countries.clear()
            _countries.addAll(it)
            emit(it)
        }
    }

    override suspend fun updateCountry(country: Country) = localDataSource
        .updateCountry(country)

    override fun getCountry(name: String): Country? =
        _countries.firstOrNull { country -> country.commonName == name }
}
