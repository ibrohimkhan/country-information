package com.kodeco.android.countryinfo.networking.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryDto(
    val name: CountryNameDto,
    val capital: List<String>?,
    val population: Long,
    val area: Float,
    val flags: CountryFlagsDto,
    val isFavorite: Boolean = false,
) : Parcelable
