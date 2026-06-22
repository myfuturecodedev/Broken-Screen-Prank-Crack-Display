package com.futurecode.crackdisplayprank.model

/**
 * 15-Year Developer Standard: Model defines both background wallpaper
 * and crack overlay resource IDs to prevent UI state mismatch during list recycle.
 */
data class EffectItem(
    val id: String,
    val backgroundImageResId: Int, // Dynamic simulated mobile OS background (OLED lines, normal OS, black screen, liquid bleeding)
    val crackImageResId: Int,       // Transparent front screen glass shatter vector / bullet hole PNG
    val styleCategory: String,      // Category name (e.g. SPIDER, LED_LINES, ELECTRIC, GLITCH)
    val hasAdsEnabled: Boolean = false
)