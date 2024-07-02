package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading
import kotlinx.coroutines.delay


@Composable
fun CountryInfoScreen(
    countryInfoViewModel: CountryInfoViewModel,
    onCountryClicked: (String) -> Unit,
    navigateToAboutScreen: () -> Unit,
) {
    val message = stringResource(R.string.something_went_wrong)
    val state by countryInfoViewModel.uiState.collectAsState()

    val favoriteFeatureEnabled by countryInfoViewModel.favoritesFeatureEnabled.collectAsState(false)
    val localStorageFeatureEnabled by countryInfoViewModel.localStorageEnabled.collectAsState(false)
    val favoriteCountriesFeatureEnabled by countryInfoViewModel.favoriteCountriesFeatureEnabled.collectAsState(false)

    val intent = when {
        favoriteCountriesFeatureEnabled -> CountryInfoIntent.LoadFavoriteCountries
        localStorageFeatureEnabled -> CountryInfoIntent.LoadCountriesFromLocalStorage
        else -> CountryInfoIntent.LoadCountries
    }

    // updates ui each time intent changes (offline mode works much better)
    LaunchedEffect(intent) {
        delay(500)
        countryInfoViewModel.processIntent(intent)
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state is UiState.Loading,
        onRefresh = {
            countryInfoViewModel.processIntent(intent)
        }
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
            is UiState.Initial -> Unit

            is UiState.Loading -> Loading()

            is UiState.Error -> CountryErrorScreen(
                message = currentState.throwable.message ?: message
            ) {
                countryInfoViewModel.processIntent(intent)
            }

            is UiState.Success -> CountryInfoList(
                countries = currentState.countries,
                onCountryClicked = onCountryClicked,
                onFavoriteClicked = { country ->
                    if (favoriteCountriesFeatureEnabled) {
                        // still issue with refreshing list after like
                        countryInfoViewModel.processIntent(
                            CountryInfoIntent.LikeAndRefresh(
                                country.copy(isFavorite = !country.isFavorite)
                            )
                        )
                    } else {
                        countryInfoViewModel.processIntent(
                            CountryInfoIntent.FavoriteCountry(
                                country.copy(isFavorite = !country.isFavorite)
                            )
                        )
                    }
                },
                navigateToAboutScreen = navigateToAboutScreen,
                pullRefreshState = pullRefreshState,
                isFavoritesFeatureEnabled = favoriteFeatureEnabled
            )
        }
    }
}
