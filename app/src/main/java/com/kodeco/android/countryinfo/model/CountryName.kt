package com.kodeco.android.countryinfo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryName(
    val common: String
) : Parcelable
