package com.futurecode.crackdisplayprank.viewModel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrankConfigViewModel : ViewModel() {

    private val _activationMethod = MutableStateFlow("TOUCH")
    val activationMethod: StateFlow<String> = _activationMethod.asStateFlow()

    private val _timerDelay = MutableStateFlow("OFF")
    val timerDelay: StateFlow<String> = _timerDelay.asStateFlow()

    private val _selectedEffectIndex = MutableStateFlow(0)
    val selectedEffectIndex: StateFlow<Int> = _selectedEffectIndex.asStateFlow()

    fun setActivationMethod(method: String) {
        _activationMethod.value = method
    }

    fun setTimerDelay(delay: String) {
        _timerDelay.value = delay
    }

    fun setSelectedEffect(index: Int) {
        _selectedEffectIndex.value = index
    }
}