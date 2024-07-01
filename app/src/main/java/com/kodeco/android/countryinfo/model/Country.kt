package com.kodeco.android.countryinfo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey
    val commonName: String,

    @ColumnInfo(name = "capital")
    val mainCapital: String,

    @ColumnInfo(name = "population")
    val population: Long,

    @ColumnInfo(name = "area")
    val area: Float,

    @ColumnInfo(name = "flag")
    val flagUrl: String,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
)
