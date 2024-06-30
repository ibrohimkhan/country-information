package com.kodeco.android.countryinfo.repository.remote

import com.kodeco.android.countryinfo.model.Country
import retrofit2.Response

interface CountryRemoteDataSource {
    suspend fun fetchCountries(): Response<List<Country>>
}
