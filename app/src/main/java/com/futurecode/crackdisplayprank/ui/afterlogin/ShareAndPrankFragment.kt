package com.futurecode.crackdisplayprank.ui.afterlogin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.ads.interstitial_ad.FullScreenAdsHelper
import com.futurecode.crackdisplayprank.ads.native_ad.NativeAdsHelper
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentShareAndPrankBinding

class ShareAndPrankFragment : BaseFragment<FragmentShareAndPrankBinding>(FragmentShareAndPrankBinding::inflate) {

    private lateinit var nativeAdsHelper: NativeAdsHelper
    lateinit var fullScreenAdsHelper: FullScreenAdsHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nativeAdsHelper= NativeAdsHelper(requireActivity())
        fullScreenAdsHelper= FullScreenAdsHelper(requireActivity())


        loanNativeAds()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // ✅ FIXED: Implemented dynamic Play Store link copy logic with Toast notification
        binding.btnCopyLink.setOnClickListener {
            try {
                val appPackage = requireContext().packageName
                val appLink = "https://play.google.com/store/apps/details?id=$appPackage"

                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("App Link", appLink)
                clipboard.setPrimaryClip(clip)

                // Toast notification in English as requested
                Toast.makeText(requireContext(), "Link copied to clipboard!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Failed to copy link", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShareLink.setOnClickListener {
            val appPackage = requireContext().packageName
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackage")))
            } catch (e: Exception) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackage")))
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