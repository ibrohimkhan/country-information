package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.model.CountryFlags
import com.kodeco.android.countryinfo.model.CountryName
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun CountryInfoRow(country: Country, modifier: Modifier = Modifier) {
    val unknown = stringResource(R.string.unknown)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.name, country.name.common),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = stringResource(R.string.capital, country.capital?.first() ?: unknown),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(4.dp)
            )
        }
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
            )
        )
    }
}
