package com.kodeco.android.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.repository.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.ApplicationNavigation
import com.kodeco.android.countryinfo.ui.SplashScreen
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = buildApiService()
        val repository = CountryRepositoryImpl(apiService)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    var isAnimationFinished by rememberSaveable { mutableStateOf(false) }

                    LaunchedEffect(key1 = Unit) {
                        delay(3000)
                        isAnimationFinished = true
                    }

                    when (isAnimationFinished) {
                        false -> SplashScreen()
                        true -> ApplicationNavigation(repository)
                    }
                }
            }
        }
    }
}
