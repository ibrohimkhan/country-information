package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
    countryDetailsViewModel.processIntent(CountryDetailsIntent.LoadCountryDetails(countryName))

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
                title = country.commonName,
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                iconClickAction = onBackClicked
            )
        }
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(
                    R.string.capital,
                    country.mainCapital
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

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedAsyncImage(country.flagUrl)
        }
    }
}

private enum class ImageState {
    Shrunk,
    Expanded
}

@Composable
fun AnimatedAsyncImage(flag: String) {
    var imageState by remember { mutableStateOf(ImageState.Shrunk) }
    val transition = updateTransition(targetState = imageState, label = "Image Transition")
    val size = transition.animateDp(label = "Image Size") { state ->
        when (state) {
            ImageState.Shrunk -> 90.dp
            ImageState.Expanded -> 130.dp
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(flag)
            .crossfade(true)
            .build(),

        contentDescription = stringResource(R.string.flag_description),
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .padding(8.dp)
            .width(size.value + 40.dp)
            .height(size.value)
            .clickable {
                imageState = when (imageState) {
                    ImageState.Shrunk -> ImageState.Expanded
                    ImageState.Expanded -> ImageState.Shrunk
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun CountryDetailsPreview() {
    MyApplicationTheme {
        CountryDetails(
            country = Country(
                commonName = "Tajikistan",
                mainCapital = "Dushanbe",
                population = 10_000_000,
                area = 300_000.0f,
                flagUrl = "tjk.png",
            ),
            onBackClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedAsyncImagePreview() {
    MyApplicationTheme {
        AnimatedAsyncImage("https://flagcdn.com/w320/wf.png")
    }
}
