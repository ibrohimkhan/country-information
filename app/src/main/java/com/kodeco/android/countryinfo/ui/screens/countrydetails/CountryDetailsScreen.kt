package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName
import com.kodeco.android.countryinfo.ui.components.AppBar
import com.kodeco.android.countryinfo.ui.components.CountryErrorScreen
import com.kodeco.android.countryinfo.ui.components.Loading
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme


@Composable
fun CountryDetailsScreen(
    countryName: String,
    countryDetailsViewModel: CountryDetailsViewModel,
    onBackClicked: () -> Unit,
) {
    val state by countryDetailsViewModel.state.collectAsState()

    // Emit intent to load country details
    LaunchedEffect(key1 = countryName) {
        countryDetailsViewModel.processIntent(CountryDetailsIntent.LoadCountryDetails(countryName))
    }

    BackHandler {
        onBackClicked()
    }

    when {
        state.isLoading -> Loading()
        state.error != null -> CountryErrorScreen(state.error!!)
        state.country != null -> CountryDetails(state.country!!, onBackClicked)
    }
}

@Composable
private fun CountryDetails(
    country: Country,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                title = country.name.common,
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                iconClickAction = onBackClicked
            )
        }
    ) { innerPadding ->
        Card(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(
                        R.string.capital,
                        country.capital?.firstOrNull() ?: stringResource(R.string.unknown)
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = stringResource(R.string.population, country.population),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = stringResource(R.string.area, country.area),
                    style = MaterialTheme.typography.titleMedium,
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

@Preview(showBackground = true)
@Composable
fun CountryDetailsPreview() {
    MyApplicationTheme {
        CountryDetails(
            country = Country(
                name = CountryName("Tajikistan"),
                capital = listOf("Dushanbe"),
                population = 10_000_000,
                area = 300_000.0,
                flags = CountryFlags("tjk.png")
            ),
            onBackClicked = {}
        )
    }
}
