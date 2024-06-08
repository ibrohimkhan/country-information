package com.kodeco.android.countryinfo.ui.screens.countrydetails

import androidx.lifecycle.ViewModel
import com.kodeco.android.countryinfo.repository.CountryRepository

class CountryDetailsViewModel(
    private val repository: CountryRepository
) : ViewModel() {

    fun getCountryDetails(name: String) = repository.getCountry(name)
}
