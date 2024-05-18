package com.kodeco.android.countryinfo.model.response

import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName
import com.squareup.moshi.Json

data class CountryResponse(
    @field:Json(name = "name")
    val name: CountryName,

    @field:Json(name = "capital")
    val capital: List<String>?,

    @field:Json(name = "population")
    val population: Long,

    @field:Json(name = "area")
    val area: Double,

    @field:Json(name = "flags")
    val flags: CountryFlags,
)
