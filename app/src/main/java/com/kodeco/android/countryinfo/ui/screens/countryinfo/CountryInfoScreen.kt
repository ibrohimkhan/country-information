package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
    navigateToAboutScreen: () -> Unit,
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

    val transition = updateTransition(
        targetState = state,
        label = "CountryInfoScreen"
    )

    transition.Crossfade(
        contentKey = { it.javaClass },
        animationSpec = tween(800)
    ) { currentState ->

        when (currentState) {
            is UiState.Loading -> Loading()

            is UiState.Error -> CountryErrorScreen(
                message = currentState.throwable.message ?: message
            ) {
                onReload()
            }

            is UiState.Success -> CountryInfoList(
                countries = currentState.countries,
                onCountryClicked = onCountryClicked,
                onFavoriteClicked = { country ->
                    countryInfoViewModel.processIntent(CountryInfoIntent.FavoriteCountry(country))
                },
                navigateToAboutScreen = navigateToAboutScreen,
                pullRefreshState = pullRefreshState
            )
        }
    }
}
