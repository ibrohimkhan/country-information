package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.store.CountryPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// State
data class SettingsState(
    val enableLocalStorage: Boolean = false,
    val enableFavoritesFeature: Boolean = false,
    val enableScreenRotation: Boolean = false,
)

// Intent
sealed class SettingsIntent {
    data class EnableLocalStorage(val enable: Boolean) : SettingsIntent()
    data class EnableFavoritesFeature(val enable: Boolean) : SettingsIntent()
    data class EnableScreenRotation(val enable: Boolean) : SettingsIntent()
}

class SettingsViewModel(
    private val countryPrefs: CountryPrefs
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            countryPrefs.getLocalStorageEnabled().collect { enable ->
                _state.value = _state.value.copy(enableLocalStorage = enable)
            }
        }

        viewModelScope.launch {
            countryPrefs.getFavoritesFeatureEnabled().collect { enable ->
                _state.value = _state.value.copy(enableFavoritesFeature = enable)
            }
        }

        viewModelScope.launch {
            countryPrefs.getScreenRotationEnabled().collect { enable ->
                _state.value = _state.value.copy(enableScreenRotation = enable)
            }
        }
    }

    fun processIntent(intent: SettingsIntent) {
        viewModelScope.launch {
            when (intent) {
                is SettingsIntent.EnableLocalStorage -> {
                    countryPrefs.toggleLocalStorage()
                    _state.value = _state.value.copy(enableLocalStorage = intent.enable)
                }

                is SettingsIntent.EnableFavoritesFeature -> {
                    countryPrefs.toggleFavoritesFeature()
                    _state.value = _state.value.copy(enableFavoritesFeature = intent.enable)
                }

                is SettingsIntent.EnableScreenRotation -> {
                    countryPrefs.toggleScreenRotation()
                    _state.value = _state.value.copy(enableScreenRotation = intent.enable)
                }
            }
        }
    }
}
