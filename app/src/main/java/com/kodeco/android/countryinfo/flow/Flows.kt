package com.kodeco.android.countryinfo.flow

import com.kodeco.android.countryinfo.ui.components.CountryInfoState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
object Flows {
    private val _tapFlow = MutableStateFlow(0)
    val tapFlow = _tapFlow.asStateFlow()

    private val _backFlow = MutableStateFlow(0)
    val backFlow = _backFlow.asStateFlow()

    private val _counterFlow = MutableStateFlow(0)
    val counterFlow = _counterFlow.asStateFlow()

    private val _countryInfoStateFlow = MutableStateFlow<CountryInfoState>(CountryInfoState.Loading)
    val countryInfoStateFlow = _countryInfoStateFlow.asStateFlow()

    init {
        GlobalScope.launch {
            while (true) {
                delay(1000L)
                _counterFlow.value += 1
            }
        }
    }

    fun tap() {
        _tapFlow.value += 1
    }

    fun tapBack() {
        _backFlow.value += 1
    }

    fun updateCountryInfoState(countryInfoState: CountryInfoState) {
        _countryInfoStateFlow.value = countryInfoState
    }
}