package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.data.store.CountryPrefs
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.CountryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


// State
sealed class UiState {
    data class Success(val countries: List<Country>) : UiState()

    data class Error(val throwable: Throwable) : UiState()

    data object Loading : UiState()

    data object Initial : UiState()
}

// Intent
sealed class CountryInfoIntent {
    data object LoadCountries : CountryInfoIntent()
    data object LoadFavoriteCountries : CountryInfoIntent()
    data object LoadCountriesFromLocalStorage : CountryInfoIntent()
    data class FavoriteCountry(val country: Country) : CountryInfoIntent()
    data class LikeAndRefresh(val country: Country) : CountryInfoIntent()
}

class CountryInfoViewModel(
    private val countryRepository: CountryRepository,
    countryPrefs: CountryPrefs
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    val featureEnabled = countryPrefs.getFavoritesFeatureEnabled()

    val favoriteEnabled = countryPrefs.getFavoriteCountriesFeatureEnabled().stateIn(
        viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed(1000)
    )

    val offlineEnabled = countryPrefs.getLocalStorageEnabled().stateIn(
        viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed(1000)
    )

    fun processIntent(intent: CountryInfoIntent) {
        viewModelScope.launch {
            when (intent) {
                is CountryInfoIntent.LoadCountries -> loadCountries()
                is CountryInfoIntent.FavoriteCountry -> favorite(intent.country)
                is CountryInfoIntent.LoadFavoriteCountries -> getFavoriteCountries()
                is CountryInfoIntent.LoadCountriesFromLocalStorage -> getCountriesFromLocalStorage()
                is CountryInfoIntent.LikeAndRefresh -> likeAndRefresh(intent.country)
            }
        }
    }

    private suspend fun loadCountries() {
        _uiState.value = UiState.Loading
        countryRepository.fetchCountries()
            .catch {
                _uiState.value = UiState.Error(it)
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    private suspend fun getCountriesFromLocalStorage() {
        _uiState.value = UiState.Loading
        countryRepository.getCountries()
            .catch {
                _uiState.value = UiState.Error(it)
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    private suspend fun getFavoriteCountries() {
        _uiState.value = UiState.Loading
        countryRepository.getFavoriteCountries()
            .catch {
                _uiState.value = UiState.Error(it)
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    private suspend fun likeAndRefresh(country: Country) {
        _uiState.value = UiState.Success(emptyList())

        countryRepository.updateCountry(country)
        delay(300)
        getFavoriteCountries()
    }

    private suspend fun favorite(country: Country) {
        countryRepository.updateCountry(country)
    }
}
