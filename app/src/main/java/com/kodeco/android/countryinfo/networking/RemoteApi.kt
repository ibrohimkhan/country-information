package com.kodeco.android.countryinfo.networking

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.Result
import com.kodeco.android.countryinfo.model.Result.Failure
import com.kodeco.android.countryinfo.model.Result.Success

class RemoteApi(private val apiService: RemoteApiService) {

    suspend fun getAllCountries(): Result<List<Country>> = try {
        val result = apiService.getAllCountries()
        Success(result)

    } catch (error: Throwable) {
        Failure(error)
    }
}
