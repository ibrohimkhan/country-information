package com.kodeco.android.countryinfo.ui.screens.tapinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


// State
data class TapInfoState(
    val tapCount: Int = 0,
    val backCount: Int = 0,
    val counter: Int = 0
)

// Intents
sealed class TapInfoIntent {
    object Tap : TapInfoIntent()
    object TapBack : TapInfoIntent()
}

/**
 * ViewModel for TapInfo screen.
 */
class TapInfoViewModel : ViewModel() {

    private val _state = MutableStateFlow(TapInfoState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                _state.value = _state.value.copy(counter = _state.value.counter + 1)
            }
        }
    }

    fun processIntent(intent: TapInfoIntent) {
        when (intent) {
            is TapInfoIntent.Tap -> {
                _state.value = _state.value.copy(tapCount = _state.value.tapCount + 1)
            }

            is TapInfoIntent.TapBack -> {
                _state.value = _state.value.copy(backCount = _state.value.backCount + 1)
            }
        }
    }
}
