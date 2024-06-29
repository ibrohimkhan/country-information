package com.kodeco.android.countryinfo.networking

import com.kodeco.android.countryinfo.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApiService {
    @GET("/v3.1/all?fields=name,flags,population,area,capital")
    suspend fun getAllCountries(): Response<List<Country>>
}
