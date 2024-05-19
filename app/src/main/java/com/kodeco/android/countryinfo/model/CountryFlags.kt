package com.kodeco.android.countryinfo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryFlags(
    val png: String
): Parcelable
