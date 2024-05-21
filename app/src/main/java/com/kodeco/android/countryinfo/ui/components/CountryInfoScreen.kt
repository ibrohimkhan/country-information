package com.kodeco.android.countryinfo.ui.components

import android.net.ConnectivityManager
import android.os.Parcelable
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.Result
import com.kodeco.android.countryinfo.networking.NetworkStatusChecker
import com.kodeco.android.countryinfo.networking.RemoteApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface UiState : Parcelable {
    @Parcelize
    data class Success(val countries: List<Country>) : UiState

    @Parcelize
    data class Error(val message: String) : UiState

    @Parcelize
    object Loading : UiState
}

@Composable
fun CountryInfoScreen(remoteApi: RemoteApi, navController: NavHostController?) {
    val context = LocalContext.current
    val networkStatusChecker by lazy {
        NetworkStatusChecker(context.getSystemService(ConnectivityManager::class.java))
    }

    val message = stringResource(R.string.something_went_wrong)
    var uiState by rememberSaveable { mutableStateOf<UiState>(UiState.Loading) }

    if (networkStatusChecker.isConnected()) {
        LaunchedEffect(uiState) {
            val result = remoteApi.getAllCountries()

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        uiState = UiState.Success(result.data)
                    }

                    is Result.Failure -> {
                        uiState = UiState.Error(result.error?.message ?: message)
                    }
                }
            }
        }
    } else {
        uiState = UiState.Error(message = context.getString(R.string.connection_problem))
    }

    when (uiState) {
        is UiState.Loading -> Loading()
        is UiState.Error -> CountryErrorScreen(
            message = (uiState as UiState.Error).message
        )

        is UiState.Success -> CountryInfoList(
            navController = navController,
            countries = (uiState as UiState.Success).countries
        )
    }
}

@Composable
fun AppBar(title: String, imageVector: ImageVector, iconClickAction: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { iconClickAction.invoke() }) {
                Icon(imageVector = imageVector, contentDescription = "")
            }
        }
    )
}
