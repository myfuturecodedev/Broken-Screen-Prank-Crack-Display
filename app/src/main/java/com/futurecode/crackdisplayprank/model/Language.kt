package com.futurecode.crackdisplayprank.model

/**
 * 15-Year Developer Standard: Pure Immutable Data Model.
 * Represents a single language locale and coordinates category groupings
 * for multi-view RecyclerView adapters.
 */
data class LanguageModel(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val flagEmoji: String,
    val category: String, // e.g., "POPULAR LANGUAGES", "EUROPEAN LANGUAGES"
    var isSelected: Boolean = false
)

sealed class LanguageListItem {
    data class Header(val title: String) : LanguageListItem()
    data class LanguageItem(val language: LanguageModel) : LanguageListItem()
    object AdItem : LanguageListItem() // Holds references to display Ad Cards natively inside the list
}