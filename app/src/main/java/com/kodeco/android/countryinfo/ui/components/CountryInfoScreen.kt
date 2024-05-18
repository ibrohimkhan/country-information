package com.kodeco.android.countryinfo.ui.components

import android.net.ConnectivityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.Result
import com.kodeco.android.countryinfo.networking.NetworkStatusChecker
import com.kodeco.android.countryinfo.networking.RemoteApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    var isError by rememberSaveable { mutableStateOf(false) }
    var errorInfo by rememberSaveable { mutableStateOf<String?>(null) }

    if (networkStatusChecker.isConnected()) {
        coroutineScope.launch {
            val result = remoteApi.getAllCountries()

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        countries = result.data
                    }

                    is Result.Failure -> {
                        isError = true
                        errorInfo = result.error?.message
                    }
                }
            }
        }
    } else {
        isError = true
        errorInfo = context.getString(R.string.connection_problem)
    }

    if (isError) {
        CountryErrorScreen(message = errorInfo)
    } else {
        CountryInfoList(countries = countries)
    }
}

@Preview
@Composable
fun CountryInfoScreenPreview() {}
