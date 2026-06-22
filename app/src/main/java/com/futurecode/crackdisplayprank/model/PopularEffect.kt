package com.futurecode.crackdisplayprank.model


// Represents a unique crack theme effect template
data class PopularEffect(
    val id: String,
    val title: String,
    val imageResId: Int,
    val type: String // e.g. "SPIDER", "BULLET", "LED"
)