package com.kodeco.android.countryinfo.ui.screens.tapinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TapInfoViewModel : ViewModel() {

    private val _tapFlow = MutableStateFlow(0)
    private val _backFlow = MutableStateFlow(0)
    private val _counterFlow = MutableStateFlow(0)

    val tapFlow = _tapFlow.asStateFlow()
    val backFlow = _backFlow.asStateFlow()
    val counterFlow = _counterFlow.asStateFlow()

    init {
        viewModelScope.launch {
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
}
