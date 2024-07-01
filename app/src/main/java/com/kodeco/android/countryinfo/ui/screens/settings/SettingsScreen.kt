package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.data.store.CountryPrefsImpl
import com.kodeco.android.countryinfo.ui.components.AppBar
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {

    val state by settingsViewModel.state.collectAsState()

    Scaffold(
        topBar = {
            AppBar(title = stringResource(R.string.settings))
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraLarge
                )
                .padding(16.dp)
        ) {
            ConfigurationItem(label = stringResource(R.string.enable_local_storage), value = state.enableLocalStorage) {
                settingsViewModel.processIntent(SettingsIntent.EnableLocalStorage(it))
            }

            ConfigurationItem(label = stringResource(R.string.enable_favorites_feature), value = state.enableFavoritesFeature) {
                settingsViewModel.processIntent(SettingsIntent.EnableFavoritesFeature(it))
            }

            ConfigurationItem(label = stringResource(R.string.enable_screen_rotation), value = state.enableScreenRotation) {
                settingsViewModel.processIntent(SettingsIntent.EnableScreenRotation(it))
            }
        }
    }
}

@Composable
fun ConfigurationItem(label: String, value: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(modifier = Modifier.weight(0.5f))

        Switch(
            checked = value,
            onCheckedChange = { onCheckedChange(it) },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MyApplicationTheme {
        SettingsScreen(SettingsViewModel(
            CountryPrefsImpl(LocalContext.current)
        ))
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigurationItemPreview() {
    MyApplicationTheme {
        ConfigurationItem("Enable Local Storage", true) {}
    }
}
