package com.kodeco.android.countryinfo.utils

import com.kodeco.android.countryinfo.networking.dto.CountryDto
import com.kodeco.android.countryinfo.networking.moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

fun getLocalCountryList(): List<CountryDto>? {
    val type: Type = Types.newParameterizedType(List::class.java, CountryDto::class.java)
    val adapter = moshi().adapter<List<CountryDto>>(type)
    return adapter.fromJson(data)
}
