package com.kodeco.android.countryinfo.utils

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.networking.moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

fun getLocalCountryList(): List<Country>? {
    val type: Type = Types.newParameterizedType(List::class.java, Country::class.java)
    val adapter = moshi().adapter<List<Country>>(type)
    return adapter.fromJson(data)
}
