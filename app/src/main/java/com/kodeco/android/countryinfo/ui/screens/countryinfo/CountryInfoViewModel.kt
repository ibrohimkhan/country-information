package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryInfoViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
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
