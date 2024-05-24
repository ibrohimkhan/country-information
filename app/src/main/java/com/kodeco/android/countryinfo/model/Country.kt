package com.kodeco.android.countryinfo.model

import android.net.Uri
import android.os.Parcelable
import com.kodeco.android.countryinfo.networking.moshi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val flags: CountryFlags,
) : Parcelable

fun Country.toJson(): String = moshi()
    .adapter(Country::class.java)
    .toJson(
        this.copy(
            flags = CountryFlags(Uri.encode(this.flags.png))
        )
    )

fun String.fromJson(): Country? = moshi()
    .adapter(Country::class.java)
    .fromJson(this)
