package com.futurecode.crackdisplayprank.ui.afterlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentSettingBinding

class SettingsFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPreferenceStates()
        setupListeners()
    }

    private fun setupPreferenceStates() {
        // Read configuration settings from preference manager cleanly
      //  binding.switchVibrate.isChecked = prefManager.isVibrationEnabled
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

        // 3. Vibrate Toggle Switch (Synchronizes with local storage)
//        binding.switchVibrate.setOnCheckedChangeListener { _, isChecked ->
//            prefManager.isVibrationEnabled = isChecked
//            val stateText = if (isChecked) "Haptic Feedback Enabled" else "Haptic Feedback Disabled"
//            Toast.makeText(requireContext(), stateText, Toast.LENGTH_SHORT).show()
//        }

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
//            val shareText = "Hey! Check out this awesome Broken Screen Prank app! Shatter your phone screen realistically. Download now: https://play.google.com/store/apps/details?id=${requireContext().packageName}"
//            val intent = Intent(Intent.ACTION_SEND).apply {
//                type = "text/plain"
//                putExtra(Intent.EXTRA_TEXT, shareText)
//            }
//            startActivity(Intent.createChooser(intent, "Share Prank via"))

            findNavController().navigate(R.id.action_global_to_shareAndPrankFragment)
        }

        // 7. Privacy Policy Browser Navigation
        binding.layoutPrivacy.setOnClickListener {
            val url = "https://www.google.com" // Update directly with your specific static policy URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // 8. Bottom Trivia Ad interaction click handler
//        binding.btnAdPlay.setOnClickListener {
//            Toast.makeText(requireContext(), "Launching Trivia Webview Game...", Toast.LENGTH_SHORT).show()
//        }
    }
}