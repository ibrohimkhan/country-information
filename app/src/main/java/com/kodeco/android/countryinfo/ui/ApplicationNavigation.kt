package com.kodeco.android.countryinfo.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.repository.CountryRepository
import com.kodeco.android.countryinfo.repository.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModelFactory
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModelFactory
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme


const val COUNTRY_KEY = "countryName"

@Composable
fun ApplicationNavigation(repository: CountryRepository) {

    val navController = rememberNavController()

    val countryInfoViewModel: CountryInfoViewModel = viewModel(
        factory = CountryInfoViewModelFactory(repository)
    )

    val viewModel: CountryDetailsViewModel = viewModel(
        factory = CountryDetailsViewModelFactory(repository)
    )

    val tapInfoViewModel: TapInfoViewModel = viewModel()

    NavHost(navController = navController, startDestination = "countries") {
        composable(route = "countries") {
            CountryInfoScreen(
                countryInfoViewModel = countryInfoViewModel,
                tapInfoViewModel = tapInfoViewModel,
                navController = navController
            )
        }

        composable(
            route = "countryDetails/{$COUNTRY_KEY}",
            arguments = listOf(
                navArgument(COUNTRY_KEY) {
                    type = NavType.StringType
                },
            ),
        ) {
            val country = it.arguments!!.getString(COUNTRY_KEY)!!

            CountryDetailsScreen(
                countryName = country,
                countryDetailsViewModel = viewModel,
                tapInfoViewModel = tapInfoViewModel,
                navController = navController
            ) {
                countryInfoViewModel.loadCountries()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicationNavigationPreview() {
    val repository = CountryRepositoryImpl(buildApiService())

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ApplicationNavigation(repository)
        }
    }
}
