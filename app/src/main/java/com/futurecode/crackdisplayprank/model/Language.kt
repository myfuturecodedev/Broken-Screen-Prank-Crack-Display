package com.futurecode.crackdisplayprank.model

//data class LanguageModel(
//    val languageCode: String,
//    val nativeName: Int,
//    val localizedCountry: Int,
//    val flagIconRes: Int,
//    var isSelected: Boolean = false,
//    val isSuggested: Boolean = false
//)



// Ek single language ka data handle karne ke liye
data class LanguageModel(
    val code: String,            // Language Code (jaise: "en", "hi", "es")
    val nativeName: String,      // Native Name (jaise: "English", "हिन्दी", "Español")
    val englishName: String,     // English Name (jaise: "English", "Hindi", "Spanish")
    val flagEmoji: String,       // Desh ka Flag (jaise: "🇺🇸", "🇮🇳", "🇪🇸")
    val category: String,        // Category Group (jaise: "POPULAR LANGUAGES", "EUROPEAN LANGUAGES")
    var isSelected: Boolean = false // Track karne ke liye ki ye selected hai ya nahi (Default: false)
)

// Section Grouping (Headers) ko support karne ke liye helper class
sealed class LanguageListItem {
    data class Header(val title: String) : LanguageListItem()
    data class LanguageItem(val language: LanguageModel) : LanguageListItem()
}