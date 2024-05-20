package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.networking.RemoteApi
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.ui.components.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = buildApiService()
        val remoteApi = RemoteApi(apiService)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    ApplicationNavigation(remoteApi)
                }
            }
        }
    }
}

const val COUNTRY_NAME_KEY = "countryName"
const val COUNTRY_CAPITAL_KEY = "countryCapital"
const val COUNTRY_POPULATION_KEY = "countryPopulation"
const val COUNTRY_AREA_KEY = "countryArea"
const val COUNTRY_URL_KEY = "countryUrl"

@Composable
fun ApplicationNavigation(remoteApi: RemoteApi) {
    val navController = rememberNavController()
    val unknown = stringResource(R.string.unknown)

    NavHost(navController = navController, startDestination = "countries") {
        composable(route = "countries") {
            CountryInfoScreen(remoteApi, navController)
        }

        composable(
            route = "countryDetails/{$COUNTRY_NAME_KEY}/{$COUNTRY_CAPITAL_KEY}/{$COUNTRY_POPULATION_KEY}/{$COUNTRY_AREA_KEY}/{$COUNTRY_URL_KEY}",
            arguments = listOf(
                navArgument(COUNTRY_NAME_KEY) {
                    type = NavType.StringType
                },
                navArgument(COUNTRY_CAPITAL_KEY) {
                    type = NavType.StringType
                },
                navArgument(COUNTRY_POPULATION_KEY) {
                    type = NavType.LongType
                },
                navArgument(COUNTRY_AREA_KEY) {
                    type = NavType.StringType
                },
                navArgument(COUNTRY_URL_KEY) {
                    type = NavType.StringType
                },
            )
        ) { backStackEntry ->

            val name = backStackEntry.arguments?.getString(COUNTRY_NAME_KEY) ?: unknown
            val capital = backStackEntry.arguments?.getString(COUNTRY_CAPITAL_KEY) ?: unknown
            val population = backStackEntry.arguments?.getLong(COUNTRY_POPULATION_KEY) ?: 0
            val area = backStackEntry.arguments?.getString(COUNTRY_AREA_KEY) ?: unknown
            val url = backStackEntry.arguments?.getString(COUNTRY_URL_KEY) ?: unknown

            CountryDetailsScreen(
                name,
                capital,
                population,
                area.toDouble(),
                url,
                navController
            )
        }
    }
}
