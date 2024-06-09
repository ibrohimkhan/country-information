package com.kodeco.android.countryinfo.ui.screens.tapinfo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TapInfoViewModel : ViewModel() {
    val tapFlow = _tapFlow.asStateFlow()
    val backFlow = _backFlow.asStateFlow()
    val counterFlow = _counterFlow.asStateFlow()

    @OptIn(DelicateCoroutinesApi::class)
    companion object {
        private val _tapFlow = MutableStateFlow(0)
        private val _backFlow = MutableStateFlow(0)
        private val _counterFlow = MutableStateFlow<Int>(0)

        init {
            // Operating on the whole application lifetime. It showed better performance in UI then
            // viewModelScope during the tests
            GlobalScope.launch {
                while (true) {
                    delay(1000L)
                    _counterFlow.value += 1
                }
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
