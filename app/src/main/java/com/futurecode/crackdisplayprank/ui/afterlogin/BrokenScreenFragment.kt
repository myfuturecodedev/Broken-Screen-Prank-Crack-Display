package com.futurecode.crackdisplayprank.ui.afterlogin

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.adapter.EffectsAdapter
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentBrokenScreenBinding
import com.futurecode.crackdisplayprank.model.EffectItem
import com.futurecode.crackdisplayprank.utils.GridSpacingItemDecoration

class BrokenScreenFragment : BaseFragment<FragmentBrokenScreenBinding>(FragmentBrokenScreenBinding::inflate) {
    private var mediaPlayer: MediaPlayer? = null

    private lateinit var effectsAdapter: EffectsAdapter
    private val effectsList = ArrayList<EffectItem>()
    private var prankType: String = ""
    private var prankTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Safe extraction with default fallback values to prevent NullPointerExceptions
        prankType = arguments?.getString("PRANK_TYPE") ?: "DEFAULT_TYPE"
        prankTitle = arguments?.getString("PRANK_TITLE") ?: "Broken Screen"


        initEffectsData()
        setupRecyclerView()
        setupListeners()
        binding.tvHeaderTitle.text = prankTitle


        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initEffectsData() {

        effectsList.clear()

        /* ✅ CRITICAL COMPLIANCE: Direct dynamic image assignment for BOTH background OS mockups
           and transparent crack glass structures matching your 15 mock screen variants.
        */
        effectsList.add(EffectItem("1", R.drawable.onboard_one, R.drawable.onboard_one, "SPIDER"))
        effectsList.add(EffectItem("2", R.drawable.onboard_two, R.drawable.onboard_two, "LED_LINES"))
        effectsList.add(EffectItem("3", R.drawable.onboard_three, R.drawable.onboard_three, "RAINBOW_LINES"))
        effectsList.add(EffectItem("4", R.drawable.onboard_one, R.drawable.onboard_one, "BULLET_HOLES"))
        effectsList.add(EffectItem("5", R.drawable.onboard_two, R.drawable.onboard_two, "GLASS_BURST"))
        effectsList.add(EffectItem("6", R.drawable.onboard_three, R.drawable.onboard_three, "SIDE_CRACK"))
        effectsList.add(EffectItem("7", R.drawable.onboard_one, R.drawable.onboard_one, "RAINBOW_GLITCH"))
        effectsList.add(EffectItem("8", R.drawable.onboard_two, R.drawable.onboard_two, "RED_BURN_OVERLAY"))
        effectsList.add(EffectItem("9", R.drawable.onboard_three, R.drawable.onboard_three, "VERTICAL_STRIPES"))
        effectsList.add(EffectItem("10", R.drawable.onboard_one, R.drawable.onboard_one, "DENSE_GLASS_CRACKS"))
        effectsList.add(EffectItem("11", R.drawable.onboard_two, R.drawable.onboard_two, "HORIZONTAL_LINE_GLITCH"))
        effectsList.add(EffectItem("12", R.drawable.onboard_three, R.drawable.onboard_three, "BULLET_MULTIPLE_SHATTERS"))
        effectsList.add(EffectItem("13", R.drawable.onboard_one, R.drawable.onboard_one, "GREEN_LINE_BURN"))
        effectsList.add(EffectItem("14", R.drawable.onboard_two, R.drawable.onboard_two, "CENTER_BULLET"))
        effectsList.add(EffectItem("15", R.drawable.onboard_three, R.drawable.onboard_three, "EDGE_SPIDER_WEB"))
    }

    private fun setupRecyclerView() {
        effectsAdapter = EffectsAdapter { selectedEffect ->
            navigateToPrankExecution(selectedEffect)
        }

        binding.rvEffects.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)

            // Proportional edge gap mapping using standard SDK units or sdp configurations
            val spacingInDp = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._8sdp)
            addItemDecoration(GridSpacingItemDecoration(2, spacingInDp, true))

            adapter = effectsAdapter
        }

        effectsAdapter.submitList(effectsList)
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnQuickLayout.setOnClickListener {
            findNavController().navigate(R.id.action_global_to_shareAndPrankFragment)
        }

        binding.btnSettings.setOnClickListener {
           // Toast.makeText(requireContext(), "Opening Preferences Panel", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_global_to_settingFragment)

        }
    }

    private fun navigateToPrankExecution(effect: EffectItem) {
//        prefManager.selectedLanguage = effect.styleCategory // Pass configuration to storage delegate
//        Toast.makeText(requireContext(), "Selected Crack Category: ${effect.styleCategory}", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_global_to_brokenScreenPreviewFragment)

      //  startBrokenEffect()

    }


    private fun startBrokenEffect(){
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(
            requireContext(),
            R.raw.sound_broken
        )

        mediaPlayer?.start()

        binding.root.postDelayed({

            if (isAdded) {
                findNavController().navigate(R.id.action_global_to_brokenEffectFragment)
            }

        }, 500)
    }
}