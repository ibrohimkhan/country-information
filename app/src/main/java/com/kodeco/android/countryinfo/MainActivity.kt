package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kodeco.android.countryinfo.networking.RemoteApi
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.ui.components.ApplicationNavigation
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
