package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.model.fromJson
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

const val COUNTRY_KEY = "countries"

@Composable
fun ApplicationNavigation(remoteApi: RemoteApi) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "countries") {
        composable(route = "countries") {
            CountryInfoScreen(remoteApi, navController)
        }

        composable(
            route = "countryDetails/{$COUNTRY_KEY}",
            arguments = listOf(
                navArgument(COUNTRY_KEY) {
                    type = NavType.StringType
                },
            ),
        ) {
            it.arguments?.getString(COUNTRY_KEY)?.fromJson()?.let {
                CountryDetailsScreen(it, navController)
            }
        }
    }
}
