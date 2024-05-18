package com.kodeco.android.countryinfo.model

data class Country(
    val name: CountryName,
    val capital: List<String>?,
    val population: Long,
    val area: Double,
    val flags: CountryFlags,
)
