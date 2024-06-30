package com.kodeco.android.countryinfo.repository.local

import com.kodeco.android.countryinfo.data.db.CountryDao
import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

class CountryLocalDataSourceImpl(
    private val countryDao: CountryDao
) : CountryLocalDataSource {

    override fun getAllCountriesFlow(): Flow<List<Country>?> = countryDao.getAll()

    override fun getFavoriteCountries(): Flow<List<Country>?> = countryDao.getFavorites()

    override suspend fun getCountryBy(name: String): Country? = countryDao.getCountryByName(name)

    override suspend fun updateCountry(country: Country) = countryDao.update(country)

    override suspend fun addCountry(vararg country: Country) = countryDao.insertAll(*country)

    override suspend fun deleteCountry(country: Country) = countryDao.delete(country)
}
