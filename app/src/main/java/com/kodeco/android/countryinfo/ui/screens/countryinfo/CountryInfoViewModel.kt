package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.store.CountryPrefs
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


// State
sealed class UiState {
    data class Success(val countries: List<Country>) : UiState()

    data class Error(val throwable: Throwable) : UiState()

    data object Loading : UiState()
}

data class UiPrefState(
    val enableLocalStorage: Boolean = false,
    val enableFavoritesFeature: Boolean = false,
)

// Intent
sealed class CountryInfoIntent {
    data object LoadCountries : CountryInfoIntent()
    data class FavoriteCountry(val country: Country) : CountryInfoIntent()
}

class CountryInfoViewModel(
    private val countryRepository: CountryRepository,
    private val countryPrefs: CountryPrefs
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiPrefState = MutableStateFlow(UiPrefState())
    val uiPrefState = _uiPrefState.asStateFlow()

    init {
        viewModelScope.launch {
            countryPrefs.getLocalStorageEnabled().collect { enable ->
                _uiPrefState.value = _uiPrefState.value.copy(enableLocalStorage = enable)
            }
        }

        viewModelScope.launch {
            countryPrefs.getFavoritesFeatureEnabled().collect { enable ->
                _uiPrefState.value = _uiPrefState.value.copy(enableFavoritesFeature = enable)
            }
        }

        viewModelScope.launch {
            countryRepository.countries
                .catch {
                    _uiState.value = UiState.Error(it)
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }

        viewModelScope.launch {
            delay(1000)
            loadCountries()
        }
    }

    fun processIntent(intent: CountryInfoIntent) {
        viewModelScope.launch {
            when (intent) {
                is CountryInfoIntent.LoadCountries -> loadCountries()
                is CountryInfoIntent.FavoriteCountry -> favorite(intent.country)
            }
        }
    }

    private suspend fun loadCountries() {
        _uiState.value = UiState.Loading

        try {
            if (_uiPrefState.value.enableLocalStorage) {
                countryRepository.getCountries()

            } else {
                countryRepository.fetchCountries()
            }

        } catch (e: Throwable) {
            _uiState.value = UiState.Error(e)
        }
    }

    private fun favorite(country: Country) {
        countryRepository.favorite(country)
    }
}
