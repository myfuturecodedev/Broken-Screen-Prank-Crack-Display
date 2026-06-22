package com.futurecode.crackdisplayprank.ui.afterlogin

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.adapter.PopularEffectAdapter
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentHomeBinding
import com.futurecode.crackdisplayprank.model.PopularEffect
import com.futurecode.crackdisplayprank.utils.OverlayPermissionHelper

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var popularAdapter: PopularEffectAdapter
    private val effectsData = ArrayList<PopularEffect>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAppLogoHeader()
        initMockData()
        setupPopularEffectsList()
        setupListeners()

        val packageName: String = requireContext().packageName
        Log.d("WARecoveryFragment", "gcgjff$packageName")

    }

    /**
     * Styled App Logo Title matching Figma "Fake" (White) + "Broken" (Cyan Gradient) exactly
     */
    private fun setupAppLogoHeader() {
        val fullText = "FakeBroken"
        val builder = SpannableStringBuilder(fullText)

        // Find position where "Broken" starts
        val startIndex = fullText.indexOf("Broken")
        if (startIndex != -1) {
            val cyanColor = ContextCompat.getColor(requireContext(), R.color.lang_cyan_glow)
            builder.setSpan(
                ForegroundColorSpan(cyanColor),
                startIndex,
                fullText.length,
                SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        binding.tvAppLogo.text = builder
    }

    private fun initMockData() {
        effectsData.clear()

        // Populate standard visual damage presets matching design screen items
        effectsData.add(
            PopularEffect("1", "Spider Crack", R.drawable.ic_spider, "SPIDER")
        )
        effectsData.add(
            PopularEffect("2", "Bullet Impact", R.drawable.ic_bullet, "BULLET")
        )
        effectsData.add(
            PopularEffect("3", "LED Color Screen", R.drawable.ic_led, "LED")
        )
    }

    private fun setupPopularEffectsList() {
        popularAdapter = PopularEffectAdapter(effectsData) { effect ->
            navigateToPrankConfiguration(effect.type, effect.title)
        }

        binding.rvPopularEffects.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvPopularEffects.adapter = popularAdapter
    }

    private fun setupListeners() {
        // Hero start prank click routing
        binding.btnStartPrank.setOnClickListener {
            navigateToPrankConfiguration("DEFAULT", "Glass Break")
        }

        // Quick Actions Grid Routing
        binding.btnActionSpider.setOnClickListener {
            navigateToPrankConfiguration("SPIDER", "Spider Crack")
        }

        binding.btnActionTouch.setOnClickListener {
            //navigateToPrankConfiguration("TOUCH", "Touch Spark")
            if (OverlayPermissionHelper.hasPermission(requireContext())) {
                navigateToPrankConfiguration("TOUCH", "Touch Spark")
            } else {
                OverlayPermissionHelper.openSettings(requireActivity())
            }
        }

        binding.btnActionBullet.setOnClickListener {
            navigateToPrankConfiguration("BULLET", "Bullet Glass Damage")
        }

        binding.btnActionLed.setOnClickListener {
            if (OverlayPermissionHelper.hasPermission(requireContext())) {
                navigateToPrankConfiguration("LED", "Screen LED Glow")
            } else {
                OverlayPermissionHelper.openSettings(requireActivity())
            }
        }

        // Action Toolbar Triggers
        binding.btnSettings.setOnClickListener {
            // Safe Jetpack navigation sequence directly to settings screen
            // findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            //Toast.makeText(requireContext(), "Opening Settings...", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_global_to_settingFragment)
        }

        binding.btnQuickWidget.setOnClickListener {
            // Toast.makeText(requireContext(), "Opening Screen Widgets Panel...", Toast.LENGTH_SHORT).show()
            // findNavController().navigate(R.id.action_global_to_brokenScreenPreviewFragment)
            findNavController().navigate(R.id.action_global_to_shareAndPrankFragment)
        }



        binding.btnSeeAll.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Viewing All Premium Effects Collection",
                Toast.LENGTH_SHORT
            ).show()
        }

        // AD button play trigger
        binding.btnAdBannerPlay.setOnClickListener {
            Toast.makeText(requireContext(), "Launching Tech Quiz...", Toast.LENGTH_SHORT).show()
        }


        binding.btnSeeAll.setOnClickListener {
            navigateToPrankConfiguration("DEFAULT", "Glass Break")

        }
    }

    private fun navigateToPrankConfiguration(type: String, title: String) {
        // Cache parameters inside Base prefManager or pass directly inside bundle
        prefManager.selectedLanguage = type // Custom reuse state pattern

        // Jetpack Navigation routing framework triggers

        val bundle = Bundle().apply {
            putString("PRANK_TYPE", type)
            putString("PRANK_TITLE", title)
        }
        findNavController().navigate(R.id.action_homeFragment_to_brokenScreenFragment, bundle)

        Toast.makeText(requireContext(), "Launching config details for: $title", Toast.LENGTH_SHORT)
            .show()
    }
}