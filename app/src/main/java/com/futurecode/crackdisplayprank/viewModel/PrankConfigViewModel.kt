package com.futurecode.crackdisplayprank.viewModel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 15-Year Developer Standard: ViewModel managing the reactive state of the prank configuration,
 * decoupling UI interactions from business logic using Kotlin StateFlow.
 */
//class PrankConfigViewModel : ViewModel() {
//
//    private val _activationMethod = MutableStateFlow("TOUCH")
//    val activationMethod: StateFlow<String> = _activationMethod.asStateFlow()
//    private val _timerDelay = MutableStateFlow("OFF")
//    val timerDelay: StateFlow<String> = _timerDelay.asStateFlow()
//
//    //private val _timerDelay = MutableStateFlow("10s")
//
//    fun setActivationMethod(method: String) {
//        _activationMethod.value = method
//    }
//
//    fun setTimerDelay(delay: String) {
//        _timerDelay.value = delay
//    }
//}



/**
 * 15-Year Developer Standard: Unidirectional Data Flow pattern
 * to handle configuration changes and state mutations asynchronously.
 */
//class PrankConfigViewModel : ViewModel() {
//
//    private val _activationMethod = MutableStateFlow("TOUCH")
//    val activationMethod: StateFlow<String> = _activationMethod.asStateFlow()
//
//    private val _timerDelay = MutableStateFlow("OFF")
//    val timerDelay: StateFlow<String> = _timerDelay.asStateFlow()
//
//    private val _selectedEffectIndex = MutableStateFlow(0)
//    val selectedEffectIndex: StateFlow<Int> = _selectedEffectIndex.asStateFlow()
//
//    fun setActivationMethod(method: String) {
//        _activationMethod.value = method
//    }
//
//    fun setTimerDelay(delay: String) {
//        _timerDelay.value = delay
//    }
//
//    fun setSelectedEffect(index: Int) {
//        _selectedEffectIndex.value = index
//    }
//}



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