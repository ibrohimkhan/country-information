package com.kodeco.android.countryinfo.ui.screens.countryinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodeco.android.countryinfo.data.store.CountryPrefs
import com.kodeco.android.countryinfo.repository.CountryRepository

class CountryInfoViewModelFactory(
    private val countryRepository: CountryRepository,
    private val countryPrefs: CountryPrefs
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CountryInfoViewModel(countryRepository, countryPrefs) as T
}
