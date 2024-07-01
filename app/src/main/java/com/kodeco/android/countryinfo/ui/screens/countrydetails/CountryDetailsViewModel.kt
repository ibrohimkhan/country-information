package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.model.Country
import com.kodeco.android.countryinfo.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// State
data class CountryDetailsState(
    val country: Country? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

// Intent
sealed class CountryDetailsIntent {
    data class LoadCountryDetails(val countryName: String) : CountryDetailsIntent()
}

class CountryDetailsViewModel(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CountryDetailsState(isLoading = true))
    val state = _state.asStateFlow()

    fun processIntent(intent: CountryDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is CountryDetailsIntent.LoadCountryDetails -> loadCountryDetails(intent.countryName)
            }
        }
    }

    private fun loadCountryDetails(name: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)

        try {
            val country = countryRepository.getCountry(name)
            _state.value = _state.value.copy(country = country, isLoading = false)

        } catch (e: Exception) {
            _state.value = _state.value.copy(isLoading = false, error = e.message)
        }
    }
}
