package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfo
import com.kodeco.android.countryinfo.ui.screens.countryinfo.AppBar
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoViewModel

@Composable
fun CountryDetailsScreen(
    countryName: String,
    countryDetailsViewModel: CountryDetailsViewModel,
    tapInfoViewModel: TapInfoViewModel,
    navController: NavHostController?,
    onRefresh: () -> Unit = {}
) {
    val country = countryDetailsViewModel.getCountryDetails(countryName) ?: return

    BackHandler {
        tapInfoViewModel.tapBack()
        navController?.navigateUp()
    }

    Column {
        TapInfo(viewModel = tapInfoViewModel) {
            navController?.navigateUp()
            onRefresh()
        }
        Scaffold(topBar = {
            AppBar(
                title = country.name.common,
                imageVector = Icons.AutoMirrored.Filled.ArrowBack
            ) {
                tapInfoViewModel.tapBack()
                navController?.navigateUp()
            }
        }) { innerPadding ->
            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.capital,
                            country.capital?.firstOrNull() ?: stringResource(R.string.unknown)
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.population, country.population),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = stringResource(R.string.area, country.area),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(country.flags.png)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(R.string.flag_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}
