package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kodeco.android.countryinfo.data.store.CountryPrefs

class SettingsViewModelFactory(
    private val countryPrefs: CountryPrefs
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SettingsViewModel(countryPrefs) as T
}
