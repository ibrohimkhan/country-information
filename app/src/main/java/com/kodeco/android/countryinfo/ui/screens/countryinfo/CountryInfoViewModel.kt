package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
    object LoadCountries : CountryInfoIntent()
}

class CountryInfoViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        processIntent(CountryInfoIntent.LoadCountries)
    }

    fun processIntent(intent: CountryInfoIntent) {
        viewModelScope.launch {
            when (intent) {
                is CountryInfoIntent.LoadCountries -> loadCountries()
            }
        }
    }

    private fun loadCountries() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            repository.fetchCountries()
                .catch {
                    _uiState.value = UiState.Error(it)
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}
