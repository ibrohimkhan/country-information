package com.kodeco.android.countryinfo.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun CountryInfoRow(
    country: Country,
    clickAction: (Country) -> Unit,
    onFavoriteClicked: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { clickAction(country) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.name, country.commonName),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = stringResource(
                    R.string.capital,
                    country.mainCapital
                ),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(4.dp)
            )
        }

        FavoriteButton(
            country = country,
            onFavoriteClicked = onFavoriteClicked
        )
    }
}

@Composable
fun FavoriteButton(country: Country, onFavoriteClicked: (Country) -> Unit) {
    var isFavorite by remember { mutableStateOf(country.isFavorite) }

    val rotationAnimation = animateFloatAsState(
        label = "rotationAnimation",
        targetValue = if (isFavorite) 360f else 0f
    )

    val painter =
        if (isFavorite) painterResource(id = R.drawable.star_filled)
        else painterResource(id = R.drawable.star_outline)

    IconButton(
        onClick = {
            isFavorite = !isFavorite
            onFavoriteClicked(country)
        }
    ) {
        Icon(
            painter = painter,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(24.dp)
                .graphicsLayer(rotationZ = rotationAnimation.value)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryInfoRowPreview() {
    MyApplicationTheme {
        CountryInfoRow(
            country = Country(
                commonName = "Tajikistan",
                mainCapital = "Dushanbe",
                population = 10_000_000,
                area = 300_000.0f,
                flagUrl = "tjk.png",
            ),
            clickAction = {},
            onFavoriteClicked = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview() {
    MyApplicationTheme {
        FavoriteButton(
            country = Country(
                commonName = "Tajikistan",
                mainCapital = "Dushanbe",
                population = 10_000_000,
                area = 300_000.0f,
                flagUrl = "tjk.png",
            ),
            onFavoriteClicked = {}
        )
    }
}
