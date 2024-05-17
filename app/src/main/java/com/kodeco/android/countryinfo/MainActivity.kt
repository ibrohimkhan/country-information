package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kodeco.android.countryinfo.ui.components.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO build out the retrofit service and reference it here.
        //  Pass the service in to the CountryInfoScreen composable below.

        setContent {
            MyApplicationTheme {
                // TODO complete the composable content and provide
                //  models for Country, CountryName, and CountryFlags.
                CountryInfoScreen()
            }
        }
    }
}
