package com.kodeco.android.countryinfo.repository.remote

import com.kodeco.android.countryinfo.networking.RemoteApiService

class CountryRemoteDataSourceImpl(
    private val apiService: RemoteApiService,
) : CountryRemoteDataSource {

    override suspend fun fetchCountries() = apiService.getAllCountries()
}
