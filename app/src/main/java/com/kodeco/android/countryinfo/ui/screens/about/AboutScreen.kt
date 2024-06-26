package com.kodeco.android.countryinfo.ui.screens.about

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.BuildConfig
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.components.AppBar

@Composable
fun AboutScreen(
    onBackClick: () -> Unit
) {
    val versionName = BuildConfig.VERSION_NAME

    BackHandler {
        onBackClick()
    }

    Scaffold(topBar = {
        AppBar(
            title = stringResource(R.string.about_screen),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            iconClickAction = onBackClick
        )
    }) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.about_app_text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = stringResource(R.string.application_version, versionName),
                style = MaterialTheme.typography.bodyMedium,
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
