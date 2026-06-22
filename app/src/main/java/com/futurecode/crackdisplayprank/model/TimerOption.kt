package com.futurecode.crackdisplayprank.model

data class TimerOption(
    val title: String,
    val seconds: Long,
    var isSelected: Boolean = false
)