package com.kodeco.android.countryinfo.model.response

import com.squareup.moshi.Json

data class CountryNameResponse(
    @field:Json(name = "common")
    val common: String
)
