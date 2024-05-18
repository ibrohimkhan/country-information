package com.kodeco.android.countryinfo.model.response

import com.squareup.moshi.Json

data class CountryFlagsResponse(
    @field:Json(name = "png")
    val png: String
)
