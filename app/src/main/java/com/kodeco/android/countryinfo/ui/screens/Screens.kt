package com.kodeco.android.countryinfo.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.StackedBarChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val name: String, val path: String, val icon: ImageVector? = null) {
    object Splash : Screens("Splash", "splash")
    object CountryList : Screens("Countries", "countries", Icons.AutoMirrored.Filled.List)
    object About : Screens("About", "about", Icons.Filled.Info)
    object Settings : Screens("Settings", "settings", Icons.Filled.Settings)
    object TapInfo : Screens("TapInfo", "tapinfo", Icons.Filled.StackedBarChart)
}
