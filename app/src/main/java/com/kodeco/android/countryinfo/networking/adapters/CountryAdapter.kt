package com.kodeco.android.countryinfo.networking.adapters

import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.networking.dto.CountryDto
import com.squareup.moshi.FromJson


class CountryAdapter {
    @FromJson
    fun fromJson(countryDtoList: List<CountryDto>) : List<Country> = countryDtoList.map { countryDto ->
        Country(
            commonName = countryDto.name.common,
            mainCapital = countryDto.capital?.firstOrNull() ?: "N/A",
            population = countryDto.population,
            area = countryDto.area,
            flagUrl = countryDto.flags.png,
        )
    }
}
