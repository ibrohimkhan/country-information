package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.model.fromJson
import com.kodeco.android.countryinfo.networking.RemoteApi
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme


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
            val country = it.arguments!!.getString(COUNTRY_KEY)!!.fromJson()!!

            CountryDetailsScreen(
                country,
                navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicationNavigationPreview() {
    val remoteApi = RemoteApi(buildApiService())

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ApplicationNavigation(remoteApi)
        }
    }
}
