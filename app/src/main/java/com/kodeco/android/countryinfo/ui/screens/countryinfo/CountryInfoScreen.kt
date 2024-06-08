package com.kodeco.android.countryinfo.ui.screens.countryinfo

import android.os.Parcelable
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class UiState : Parcelable {
    data class Success(val countries: List<Country>) : UiState()

    data class Error(val throwable: Throwable) : UiState()

    data object Loading : UiState()
    data object Initial : UiState()
}

@Composable
fun CountryInfoScreen(
    viewModel: CountryInfoViewModel,
    navController: NavHostController?
) {
    val message = stringResource(R.string.something_went_wrong)
    val state = viewModel.uiState.collectAsState()

    when (state.value) {
        is UiState.Initial -> {}

        is UiState.Loading -> Loading()

        is UiState.Error -> CountryErrorScreen(
            message = (state.value as UiState.Error).throwable.message ?: message
        ) {
            viewModel.loadCountries()
        }

        is UiState.Success -> CountryInfoList(
            navController = navController,
            countries = (state.value as UiState.Success).countries,
        ) {
            viewModel.loadCountries()
        }
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
