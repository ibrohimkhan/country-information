package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodeco.android.countryinfo.repository.CountryRepository

class CountryDetailsViewModelFactory(
    private val countryRepository: CountryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CountryDetailsViewModel(countryRepository) as T
}
