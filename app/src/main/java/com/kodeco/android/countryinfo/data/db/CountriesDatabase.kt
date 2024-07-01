package com.kodeco.android.countryinfo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kodeco.android.countryinfo.model.Country

const val DATABASE_VERSION = 1

@Database(
    entities = [Country::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class CountriesDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        private const val DATABASE_NAME = "countries_db"

        @Volatile
        private var Instance: CountriesDatabase? = null

        fun getCountriesDatabase(context: Context): CountriesDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    CountriesDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
