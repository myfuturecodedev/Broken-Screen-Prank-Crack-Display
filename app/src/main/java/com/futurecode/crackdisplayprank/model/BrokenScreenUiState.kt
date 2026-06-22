package com.futurecode.crackdisplayprank.model

data class BrokenScreenUiState(
    val selectedType: ActivationType = ActivationType.TOUCH,
    val selectedTimer: TimerOption? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)