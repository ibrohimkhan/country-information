package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading


@Composable
fun CountryInfoScreen(
    countryInfoViewModel: CountryInfoViewModel,
    onCountryClicked: (String) -> Unit,
    navigateToAboutScreen: () -> Unit
) {
    val message = stringResource(R.string.something_went_wrong)
    val state by countryInfoViewModel.uiState.collectAsState()

    val onReload = {
        countryInfoViewModel.processIntent(CountryInfoIntent.LoadCountries)
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state is UiState.Loading,
        onRefresh = onReload
    )

    when (state) {
        is UiState.Initial -> Unit

        is UiState.Loading -> Loading()

        is UiState.Error -> CountryErrorScreen(
            message = (state as UiState.Error).throwable.message ?: message
        ) {
            onReload()
        }

        is UiState.Success -> CountryInfoList(
            countries = (state as UiState.Success).countries,
            onCountryClicked = onCountryClicked,
            navigateToAboutScreen = navigateToAboutScreen,
            pullRefreshState = pullRefreshState
        )
    }
}
