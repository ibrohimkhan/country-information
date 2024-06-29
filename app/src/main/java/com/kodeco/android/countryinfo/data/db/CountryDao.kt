package com.kodeco.android.countryinfo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kodeco.android.countryinfo.model.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("select * from country")
    fun getAll(): Flow<List<Country>>

    @Query("select * from country where is_favorite = 1")
    fun getFavorites(): Flow<List<Country>?>

    @Update
    suspend fun update(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg country: Country)

    @Delete
    suspend fun delete(country: Country)
}
