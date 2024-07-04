package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.ui.screens.Screens
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun CountryInfoList(
    countries: List<Country>,
    onCountryClicked: (String) -> Unit,
    onFavoriteClicked: (Country) -> Unit,
    navigateToAboutScreen: () -> Unit,
    pullRefreshState: PullRefreshState,
    isFavoritesFeatureEnabled: Boolean,
) {

    Scaffold(topBar = {
        CustomAppBar(
            title = stringResource(R.string.countries_screen),
            icon = Screens.About.icon!!,
            iconClickAction = navigateToAboutScreen
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn {
                items(countries) { country ->
                    CountryInfoRow(
                        country = country,
                        isFavoritesFeatureEnabled = isFavoritesFeatureEnabled,
                        clickAction = {
                            onCountryClicked(country.commonName)
                        },
                        onFavoriteClicked = {
                            onFavoriteClicked(country)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CountryInfoListPreview() {
    MyApplicationTheme {
        CountryInfoList(
            countries = listOf(
                Country(
                    commonName = "Tajikistan",
                    mainCapital = "Dushanbe",
                    population = 10_000_000,
                    area = 300_000.0f,
                    flagUrl = "tjk.png"
                ),
                Country(
                    commonName = "Uzbekistan",
                    mainCapital = "Tashkent",
                    population = 30_000_000,
                    area = 700_000.0f,
                    flagUrl = "uz.png"
                ),
                Country(
                    commonName = "Kazakhstan",
                    mainCapital = "Astana",
                    population = 20_000_000,
                    area = 1_000_000.0f,
                    flagUrl = "kz.png"
                ),
            ),
            onCountryClicked = {},
            onFavoriteClicked = {},
            navigateToAboutScreen = {},
            pullRefreshState = rememberPullRefreshState(
                refreshing = false,
                onRefresh = {}
            ),
            isFavoritesFeatureEnabled = false,
        )
    }
}
