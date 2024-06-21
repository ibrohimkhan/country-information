package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun CountryInfoRow(
    country: Country,
    clickAction: (Country) -> Unit,
    onFavoriteClicked: (Country) -> Unit,
    modifier: Modifier = Modifier,
) {
    val unknown = stringResource(R.string.unknown)

    Card(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { clickAction(country) }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(R.string.name, country.name.common),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = stringResource(
                        R.string.capital,
                        country.capital?.firstOrNull() ?: unknown
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
}

@Composable
fun FavoriteButton(country: Country, onFavoriteClicked: (Country) -> Unit) {
    val painter =
        if (country.isFavorite) painterResource(id = R.drawable.star_filled)
        else painterResource(id = R.drawable.star_outline)

    IconButton(onClick = { onFavoriteClicked(country) }) {
        Icon(
            painter = painter,
            contentDescription = "Favorite",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryInfoRowPreview() {
    MyApplicationTheme {
        CountryInfoRow(
            country = Country(
                name = CountryName("Tajikistan"),
                capital = listOf("Dushanbe"),
                population = 10_000_000,
                area = 300_000.0,
                flags = CountryFlags("tjk.png")
            ),
            clickAction = {},
            onFavoriteClicked = {},
        )
    }
}
