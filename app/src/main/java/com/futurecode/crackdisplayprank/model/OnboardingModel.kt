package com.futurecode.crackdisplayprank.model

//data class OnboardingModel(
//    val id: Int,
//    val title: Int,
//    val highlightedWords: List<String>,
//    val description: Int,
//    val illustrationRes: Int
//)

data class OnboardingModel(
    val titleLine1: String,
    val titleLine2: String,
    val description: String,
    val illustrationResId: Int,
    val crackBgResId: Int
)