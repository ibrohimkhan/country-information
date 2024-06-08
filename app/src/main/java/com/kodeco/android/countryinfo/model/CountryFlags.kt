package com.kodeco.android.countryinfo.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryFlags(
    val png: String
) : Parcelable
