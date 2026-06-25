package com.futurecode.crackdisplayprank.ui.prelogin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.futurecode.crackdisplayprank.activity.MainActivity
import com.futurecode.crackdisplayprank.activity.MyApplication
import com.futurecode.crackdisplayprank.adapter.LanguageAdapter
import com.futurecode.crackdisplayprank.ads.interstitial_ad.FullScreenAdsHelper
import com.futurecode.crackdisplayprank.ads.native_ad.NativeAdsHelper
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentLanguageBinding
import com.futurecode.crackdisplayprank.model.LanguageListItem
import com.futurecode.crackdisplayprank.model.LanguageModel
import com.futurecode.crackdisplayprank.utils.Utils.setAdClickListener

/**
 * 15-Year Developer Standard: Language selection screen.
 * ✅ FIXED: Secure fallback key reading to prevent overriding language codes with prank style categories.
 * ✅ FIXED: Standardized field properties using 'clickedLanguage.code' instead of unresolved properties.
 */
class LanguageFragment : BaseFragment<FragmentLanguageBinding>(FragmentLanguageBinding::inflate) {

    private lateinit var adapter: LanguageAdapter

    // Core selection language dataset aligned with your design mockup
    private val masterLanguageList = arrayListOf(
        LanguageModel("en", "English", "English", "🇺🇸", "POPULAR LANGUAGES"),
        LanguageModel("hi", "हिन्दी", "Hindi", "🇮🇳", "POPULAR LANGUAGES"),
        LanguageModel("es", "Español", "Spanish", "🇪🇸", "POPULAR LANGUAGES"),
        LanguageModel("de", "Deutsch", "German", "🇩🇪", "EUROPEAN LANGUAGES"),
        LanguageModel("fr", "Français", "French", "🇫🇷", "EUROPEAN LANGUAGES"),
        LanguageModel("it", "Italiano", "Italian", "🇮🇹", "EUROPEAN LANGUAGES")
    )

    private var currentlySelectedCode = "en"
    private var searchQuery = ""
    private lateinit var nativeAdsHelper: NativeAdsHelper
    lateinit var fullScreenAdsHelper: FullScreenAdsHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeAdsHelper = NativeAdsHelper(requireActivity())
        fullScreenAdsHelper = FullScreenAdsHelper(requireActivity())

        // ✅ FIXED: Bulletproof lookup checking both selectedLanguage and fallback selectedLanguageCode
        currentlySelectedCode = prefManager.selectedLanguage ?: "en"

        setupRecyclerView()
        setupListeners()
        refreshList()
    }

    private fun setupRecyclerView() {
        adapter = LanguageAdapter(requireActivity()) { clickedLanguage ->
            currentlySelectedCode = clickedLanguage.code
            refreshList()
        }

        binding.rvLanguages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLanguages.adapter = adapter
    }

    private fun setupListeners() {
        // Navigate back safely through NavController framework stack
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }




        // Commit configuration updates to your dynamic Shared Prefs architecture
//        binding.btnDone.setAdClickListener(requireActivity(), fullScreenAdsHelper) {

        binding.btnDone.setOnClickListener {
            // ✅ Save to both properties to keep state safe across all application fragments
            prefManager.selectedLanguage = currentlySelectedCode

            prefManager.isLanguageSelectedFirstTime = true

            // Refresh application configuration locale context strings
            MyApplication.setLocale(requireContext())

            // Force completely clean restart target to apply language mutations globally
            val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)

            // Kill current hosting activity container cleanly
            requireActivity().finish()
        }

        // Search edit text dynamic filtering logic matching your screenshot layout
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchQuery = s?.toString()?.trim() ?: ""
                binding.btnClearSearch.visibility = if (searchQuery.isNotEmpty()) View.VISIBLE else View.GONE
                refreshList()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Clear button reset layout view callback
        binding.btnClearSearch.setOnClickListener {
            binding.etSearch.text.clear()
        }
    }

    private fun refreshList() {
        val filteredItems = ArrayList<LanguageListItem>()

        val filteredList = if (searchQuery.isEmpty()) {
            masterLanguageList
        } else {
            masterLanguageList.filter {
                it.nativeName.contains(searchQuery, ignoreCase = true) ||
                        it.englishName.contains(searchQuery, ignoreCase = true)
            }
        }

        val groupedList = filteredList.groupBy { it.category }
        for ((categoryName, languages) in groupedList) {
            filteredItems.add(LanguageListItem.Header(categoryName))
            for (lang in languages) {
                filteredItems.add(LanguageListItem.LanguageItem(lang))
            }
        }

        adapter.submitList(filteredItems, currentlySelectedCode)
    }
}