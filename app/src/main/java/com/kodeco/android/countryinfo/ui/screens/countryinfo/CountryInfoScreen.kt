package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.CountryInfoList
import com.kodeco.android.countryinfo.ui.components.Loading
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoViewModel


@Composable
fun CountryInfoScreen(
    countryInfoViewModel: CountryInfoViewModel,
    tapInfoViewModel: TapInfoViewModel,
    navController: NavHostController?
) {
    val message = stringResource(R.string.something_went_wrong)
    val state by countryInfoViewModel.uiState.collectAsState()

    val onRefresh = {
        countryInfoViewModel.processIntent(CountryInfoIntent.LoadCountries)
    }

    when (state) {
        is UiState.Initial -> Unit

        is UiState.Loading -> Loading()

        is UiState.Error -> CountryErrorScreen(
            message = (state as UiState.Error).throwable.message ?: message
        ) {
            onRefresh()
        }

        is UiState.Success -> CountryInfoList(
            tapInfoViewModel = tapInfoViewModel,
            navController = navController,
            countries = (state as UiState.Success).countries,
        ) {
            onRefresh()
        }
    }
}

@Composable
fun AppBar(title: String, imageVector: ImageVector, iconClickAction: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { iconClickAction.invoke() }) {
                Icon(imageVector = imageVector, contentDescription = "")
            }
        }
    )
}
