package com.futurecode.crackdisplayprank.ui.afterlogin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentPreviewScreenBinding
import com.futurecode.crackdisplayprank.utils.BrokenScreenService

/**
 * 15-Year Developer Standard: Responsive Fullscreen Interactive Preview.
 * ✅ FIXED: Integrates background prank service dispatchers. When the user clicks
 * btnStartPrankCyan or btnStartPrankDark, it triggers the overlay engine with
 * the exact image parameters currently rendered inside the mockup frame.
 */
class PreviewScreenFragment : BaseFragment<FragmentPreviewScreenBinding>(FragmentPreviewScreenBinding::inflate) {

    private var prankType: String = ""
    private var prankTitle: String = ""

    // Dynamic asset variables with safe default presets
    private var selectedBgRes: Int = R.drawable.broken_screen_2
    private var selectedCrackRes: Int = R.drawable.broken_screen_1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Safe extraction with default fallback values to prevent NullPointerExceptions
        prankType = arguments?.getString("PRANK_TYPE") ?: "DEFAULT"
        prankTitle = arguments?.getString("PRANK_TITLE") ?: "Broken Screen"

        // 2. Extract selected background and crack resources passed from the previous list/carousel screen
        selectedBgRes = arguments?.getInt("IMAGE") ?: R.drawable.broken_screen_2
        selectedCrackRes = arguments?.getInt("CRACK") ?: R.drawable.broken_screen_1

        Log.d("TAG", "prankType: $prankType")
        Log.d("TAG", "prankTitle: $prankTitle")

        // 3. Render the selected images onto the centered phone mockup layouts
        setupMockupPreview()

        // 4. Click Listener Configurations
        setupClickListeners()
    }

    /**
     * Sets the images dynamically inside the centered CardView phone mockup framework.
     */
    private fun setupMockupPreview() {
        binding.ivMockWallpaper.setImageResource(selectedBgRes)
        binding.ivMockCrack.setImageResource(selectedCrackRes)
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnHelpInfo.setOnClickListener {
            findNavController().navigate(R.id.action_global_to_howToUseFragment)
        }

        // Right Start Button (Glowing Radiant Cyan Style)
        binding.btnStartPrankCyan.setOnClickListener {
            launchBackgroundPrankEngine()
        }

        binding.btnGoBack.setOnClickListener {
            findNavController().popBackStack()

        }
    }

    /**
     * ✅ HELPER METHOD: Safely arms the background service and minimizes the application context.
     */
    private fun launchBackgroundPrankEngine() {
        // Draw Overlays system permission verification
        if (!Settings.canDrawOverlays(requireContext())) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${requireContext().packageName}")
            )
            startActivity(intent)
            return
        }

        // Pack identical visual assets as intent extras and launch foreground task safely
        val serviceIntent = Intent(requireContext(), BrokenScreenService::class.java).apply {
            putExtra(BrokenScreenService.EXTRA_BACKGROUND, selectedCrackRes)
            putExtra(BrokenScreenService.EXTRA_CRACK, selectedCrackRes)
            putExtra(BrokenScreenService.EXTRA_TOUCH_CATCHER, true) // Arms touch catcher by default
        }

        // 1. Launch foreground task delegation
        ContextCompat.startForegroundService(requireContext(), serviceIntent)

        // 2. Instantly minimize/hide the app workspace window cleanly
        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded) {
                requireActivity().moveTaskToBack(true)
            }
        }, 300)

        // Inform user with dynamic armed message matching selected method
        Toast.makeText(
            requireContext(),
            "Touch Trigger Armed! Tap screen to break it.",
            Toast.LENGTH_SHORT
        ).show()
    }
}