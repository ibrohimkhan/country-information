package com.kodeco.android.countryinfo.ui.components

import android.net.ConnectivityManager
import android.os.Parcelable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface CountryInfoState : Parcelable {
    @Parcelize
    data class Success(val countries: List<Country>) : CountryInfoState

    @Parcelize
    data class Error(val message: String) : CountryInfoState

    @Parcelize
    data object Loading : CountryInfoState
}

@Composable
fun CountryInfoScreen(remoteApi: RemoteApi, navController: NavHostController?) {
    val context = LocalContext.current
    val networkStatusChecker by lazy {
        NetworkStatusChecker(context.getSystemService(ConnectivityManager::class.java))
    }

    val message = stringResource(R.string.something_went_wrong)
    var countryInfoState: CountryInfoState by rememberSaveable { mutableStateOf(CountryInfoState.Loading) }

    when (countryInfoState) {
        is CountryInfoState.Loading -> Loading()
        is CountryInfoState.Error -> CountryErrorScreen(
            message = (countryInfoState as CountryInfoState.Error).message
        )

        is CountryInfoState.Success -> CountryInfoList(
            navController = navController,
            countries = (countryInfoState as CountryInfoState.Success).countries
        )
    }

    if (networkStatusChecker.isConnected()) {
        LaunchedEffect("fetch-countries") {
            getCountryInfoFlow(remoteApi, message).collect {
                countryInfoState = it
            }
        }
    } else {
        countryInfoState = CountryInfoState.Error(message = context.getString(R.string.connection_problem))
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

private fun getCountryInfoFlow(remoteApi: RemoteApi, message: String): Flow<CountryInfoState> = flow {
    val countryInfoState = when (val result = remoteApi.getAllCountries()) {
        is Result.Success -> {
            CountryInfoState.Success(result.data)
        }

        is Result.Failure -> {
            CountryInfoState.Error(result.error?.message ?: message)
        }
    }

    emit(countryInfoState)
}
