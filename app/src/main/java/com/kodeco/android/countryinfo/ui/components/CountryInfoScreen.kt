package com.kodeco.android.countryinfo.ui.components

import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.Result
import com.kodeco.android.countryinfo.networking.NetworkStatusChecker
import com.kodeco.android.countryinfo.networking.RemoteApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO fill out CountryInfoScreen
@Composable
fun CountryInfoScreen(remoteApi: RemoteApi) {
    val context = LocalContext.current
    val networkStatusChecker by lazy {
        NetworkStatusChecker(context.getSystemService(ConnectivityManager::class.java))
    }

    val coroutineScope = rememberCoroutineScope {
        Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            throw throwable
        }
    }

    var countries by rememberSaveable { mutableStateOf<List<Country>>(emptyList()) }

    coroutineScope.launch {
        networkStatusChecker.performIfConnectedToInternet {
            val result = remoteApi.getAllCountries()

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        countries = result.data
                        Log.d("CountryInfoScreen", "Countries: $countries")
                    }

                    is Result.Failure -> {
                        Log.d("CountryInfoScreen", "Error: ${result.error}")
                    }
                }
            }
        }
    }
}

// TODO fill out the preview.
@Preview
@Composable
fun CountryInfoScreenPreview() {
}
