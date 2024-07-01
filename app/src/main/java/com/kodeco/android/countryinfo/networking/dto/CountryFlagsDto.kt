package com.kodeco.android.countryinfo.networking.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryFlagsDto(
    val png: String
) : Parcelable
