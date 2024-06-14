package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoViewModel


@Composable
fun CountryInfoScreen(
    countryInfoViewModel: CountryInfoViewModel,
    tapInfoViewModel: TapInfoViewModel,
    navController: NavHostController?,
    navigateToAboutScreen: () -> Unit
) {
    val message = stringResource(R.string.something_went_wrong)
    val state by countryInfoViewModel.uiState.collectAsState()

    val onRefresh = {
        countryInfoViewModel.processIntent(CountryInfoIntent.LoadCountries)
    }

    when (state) {
        is UiState.Initial -> Unit

        is UiState.Loading -> Loading()

        is UiState.Error -> CountryErrorScreen(
            message = (state as UiState.Error).throwable.message ?: message
        ) {
            onRefresh()
        }

        is UiState.Success -> CountryInfoList(
            tapInfoViewModel = tapInfoViewModel,
            navController = navController,
            countries = (state as UiState.Success).countries,
            navigateToAboutScreen = navigateToAboutScreen
        )
    }
}
