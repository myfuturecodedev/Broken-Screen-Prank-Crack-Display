package com.futurecode.crackdisplayprank.viewModel

import androidx.lifecycle.ViewModel
import com.futurecode.crackdisplayprank.model.ActivationType
import com.futurecode.crackdisplayprank.model.BrokenScreenUiState
import com.futurecode.crackdisplayprank.model.TimerOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class BrokenScreenViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(BrokenScreenUiState())

    val uiState = _uiState.asStateFlow()

    private val _timerOptions =
        MutableStateFlow(
            listOf(
                TimerOption("OFF", 0, true),
                TimerOption("10s", 10),
                TimerOption("15s", 15),
                TimerOption("20s", 20),
                TimerOption("30s", 30),
                TimerOption("1m", 60),
                TimerOption("2m", 120)
            )
        )

    val timerOptions = _timerOptions.asStateFlow()

    fun selectActivation(type: ActivationType) {
        _uiState.update {
            it.copy(selectedType = type)
        }
    }

    fun selectTimer(position: Int) {

        val updated = _timerOptions.value.mapIndexed { index, item ->
            item.copy(isSelected = index == position)
        }

        _timerOptions.value = updated

        _uiState.update {
            it.copy(selectedTimer = updated[position])
        }
    }
}