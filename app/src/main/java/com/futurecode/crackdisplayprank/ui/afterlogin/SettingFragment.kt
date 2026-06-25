package com.futurecode.crackdisplayprank.ui.afterlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.ads.interstitial_ad.FullScreenAdsHelper
import com.futurecode.crackdisplayprank.ads.native_ad.NativeAdsHelper
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentSettingBinding

class SettingsFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private lateinit var nativeAdsHelper: NativeAdsHelper
    lateinit var fullScreenAdsHelper: FullScreenAdsHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nativeAdsHelper= NativeAdsHelper(requireActivity())
        fullScreenAdsHelper= FullScreenAdsHelper(requireActivity())

        setupPreferenceStates()
        setupListeners()

        loanNativeAds()
    }

    private fun setupPreferenceStates() {
        // Read configuration settings from preference manager cleanly
        binding.switchVibrate.isChecked = prefManager.isVibrationEnabled
    }

    private fun setupListeners() {
        // 1. Back Navigation Button
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // 2. Help & Usage Interaction
        binding.layoutHowTo.setOnClickListener {
          //  Toast.makeText(requireContext(), "Opening Help Instructions...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_global_to_howToUseFragment)
        }

        // 4. Change Language Button
        binding.layoutLanguageRow.setOnClickListener {
            // Safe Jetpack navigation back to pre-login language screen context
           findNavController().navigate(R.id.action_global_to_languageFragment2)
        }

        // 5. Rate Application Trigger
        binding.layoutRateRow.setOnClickListener {
            val appPackage = requireContext().packageName
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackage")))
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")))
            }
        }

        // 6. Share Prank with Friends Action
        binding.layoutShareRow.setOnClickListener {
            findNavController().navigate(R.id.action_global_to_shareAndPrankFragment)
        }

        // 7. Privacy Policy Browser Navigation
        binding.layoutPrivacy.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(prefManager.privacyPolicy ?: "")) // Replace with actual URL
            startActivity(browserIntent)
        }


        // ✅ FIXED: Explicitly stores 'true' when enabled, and 'false' when disabled
        binding.switchVibrate.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Jab enable (checked) ho, tab explicitly 'true' store hoga
                prefManager.isVibrationEnabled = true
            } else {
                // Jab disable (unchecked) ho, tab explicitly 'false' store hoga
                prefManager.isVibrationEnabled = false
            }
        }

    }


    fun loanNativeAds(){
        nativeAdsHelper = NativeAdsHelper(requireActivity())
        nativeAdsHelper?.showNativeAd(
            nativeBannerAdView = binding.nativeAds3.frame,
            mainLayout = binding.nativeAds3.mainLayout,
            placeholder = binding.nativeAds3.placeholder
        )
    }
}