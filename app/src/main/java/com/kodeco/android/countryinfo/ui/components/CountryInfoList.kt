package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun CountryInfoList(
    countries: List<Country>,
    navController: NavHostController?,
    onRefresh: () -> Unit = {},
) {
    val tapInfoViewModel: TapInfoViewModel = viewModel()

    Column {
        TapInfo {
            onRefresh()
        }
        LazyColumn {
            items(countries) {
                CountryInfoRow(
                    country = it,
                    modifier = Modifier.padding(8.dp)
                ) { item ->
                    tapInfoViewModel.tap()
                    navController?.navigate("countryDetails/${item.name.common}")
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
            navController = null,
            countries = listOf(
                Country(
                    name = CountryName("Tajikistan"),
                    capital = listOf("Dushanbe"),
                    population = 10_000_000,
                    area = 300_000.0,
                    flags = CountryFlags("tjk.png")
                ),
                Country(
                    name = CountryName("Uzbekistan"),
                    capital = listOf("Tashkent"),
                    population = 30_000_000,
                    area = 700_000.0,
                    flags = CountryFlags("uz.png")
                ),
                Country(
                    name = CountryName("Kazakhstan"),
                    capital = listOf("Astana"),
                    population = 20_000_000,
                    area = 1_000_000.0,
                    flags = CountryFlags("kz.png")
                ),
            )
        )
    }
}
