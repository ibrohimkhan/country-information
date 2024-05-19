package com.kodeco.android.countryinfo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryName(
    val common: String
): Parcelable
