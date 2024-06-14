package com.kodeco.android.countryinfo.ui.screens.about

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.AppBar

@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    BackHandler {
        onBackClick()
    }

    Scaffold(topBar = {
        AppBar(
            title = stringResource(R.string.about_screen),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            iconClickAction = onBackClick
        )
    }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = Color.White)
        ) {
            Text(
                text = stringResource(R.string.about_app_text),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen(onBackClick = {})
}