package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kodeco.android.countryinfo.data.db.CountriesDatabase
import com.kodeco.android.countryinfo.data.store.CountryPrefsImpl
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.repository.CountryRepositoryImpl
import com.kodeco.android.countryinfo.repository.local.CountryLocalDataSourceImpl
import com.kodeco.android.countryinfo.repository.remote.CountryRemoteDataSourceImpl
import com.kodeco.android.countryinfo.ui.ApplicationNavigation
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = buildApiService()
        val remoteDataSource = CountryRemoteDataSourceImpl(apiService)

        val db = CountriesDatabase.getCountriesDatabase(this)
        val localDataSource = CountryLocalDataSourceImpl(db.countryDao())

        val prefs = CountryPrefsImpl(this)

        val countryRepository = CountryRepositoryImpl(remoteDataSource, localDataSource, prefs)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ApplicationNavigation(countryRepository)
                }
            }
        }
    }
}
