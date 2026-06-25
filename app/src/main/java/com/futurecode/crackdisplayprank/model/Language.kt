package com.futurecode.crackdisplayprank.model


// Ek single language ka data handle karne ke liye
//data class LanguageModel(
//    val code: String,            // Language Code (jaise: "en", "hi", "es")
//    val nativeName: String,      // Native Name (jaise: "English", "हिन्दी", "Español")
//    val englishName: String,     // English Name (jaise: "English", "Hindi", "Spanish")
//    val flagEmoji: String,       // Desh ka Flag (jaise: "🇺🇸", "🇮🇳", "🇪🇸")
//    val category: String,        // Category Group (jaise: "POPULAR LANGUAGES", "EUROPEAN LANGUAGES")
//    var isSelected: Boolean = false // Track karne ke liye ki ye selected hai ya nahi (Default: false)
//)
//
//sealed class LanguageListItem {
//    data class Header(val title: String) : LanguageListItem()
//    data class LanguageItem(val language: LanguageModel) : LanguageListItem()
//}



data class LanguageModel(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val flagEmoji: String,
    val category: String, // e.g., "POPULAR LANGUAGES", "EUROPEAN LANGUAGES"
    var isSelected: Boolean = false
)

// List helper wrapper classes supporting section grouping in multi-view Adapter
// ✅ UPDATED: Added 'AdItem' support inside the sealed layout list options
sealed class LanguageListItem {
    data class Header(val title: String) : LanguageListItem()
    data class LanguageItem(val language: LanguageModel) : LanguageListItem()
    object AdItem : LanguageListItem() // Holds references to display Ad Cards natively inside the list
}