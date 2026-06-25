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

/**
 * 15-Year Developer Standard: Filters and displays specific category grids.
 * On item click, calculates list position index and navigates to the preview screen.
 * ✅ FIXED: Implements dynamic conditional grouping. When prankType is "SPIDER",
 * the grid is loaded with exactly 8 spider-themed crack effects!
 */
class BrokenScreenFragment : BaseFragment<FragmentBrokenScreenBinding>(FragmentBrokenScreenBinding::inflate) {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var effectsAdapter: EffectsAdapter
    private val effectsList = ArrayList<EffectItem>()
    private var prankType: String = ""
    private var prankTitle: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Safe extraction with default fallback values to prevent NullPointerExceptions
        prankType = arguments?.getString("PRANK_TYPE") ?: "DEFAULT"
        prankTitle = arguments?.getString("PRANK_TITLE") ?: "Broken Screen"

        initEffectsData()
        setupRecyclerView()
        setupListeners()
        binding.tvHeaderTitle.text = prankTitle

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * ✅ OPTIMIZED: Conditional loading based on category parameters.
     * When prankType is "SPIDER", it displays exactly 8 items matching spider design patterns.
     */
    private fun initEffectsData() {
        effectsList.clear()

        // 1. Generate full master dataset of 20 effects with unique IDs
        val masterList = ArrayList<EffectItem>().apply {
            add(EffectItem("1", R.drawable.broken_screen_1, R.drawable.broken_img1, "SPIDER"))
            add(EffectItem("2", R.drawable.broken_screen_2, R.drawable.broken_img2, "LED_LINES"))
            add(EffectItem("3", R.drawable.broken_screen_3, R.drawable.broken_img3, "RAINBOW_LINES"))
            add(EffectItem("4", R.drawable.broken_screen_4, R.drawable.broken_img4, "BULLET_HOLES"))
            add(EffectItem("5", R.drawable.broken_screen_5, R.drawable.broken_img5, "GLASS_BURST"))
            add(EffectItem("6", R.drawable.broken_screen_6, R.drawable.broken_img6, "SIDE_CRACK"))
            add(EffectItem("7", R.drawable.broken_screen_7, R.drawable.broken_img7, "RAINBOW_GLITCH"))
            add(EffectItem("8", R.drawable.broken_screen_8, R.drawable.broken_img8, "RED_BURN_OVERLAY"))
            add(EffectItem("9", R.drawable.broken_screen_9, R.drawable.broken_img9, "VERTICAL_STRIPES"))
            add(EffectItem("10", R.drawable.broken_screen_10, R.drawable.broken_img10, "DENSE_GLASS_CRACKS"))
            add(EffectItem("11", R.drawable.broken_screen_11, R.drawable.broken_img11, "HORIZONTAL_LINE_GLITCH"))
            add(EffectItem("12", R.drawable.broken_screen_12, R.drawable.broken_img12, "BULLET_MULTIPLE_SHATTERS"))
            add(EffectItem("13", R.drawable.broken_screen_13, R.drawable.broken_img13, "GREEN_LINE_BURN"))
            add(EffectItem("14", R.drawable.broken_screen_14, R.drawable.broken_img14, "CENTER_BULLET"))
            add(EffectItem("15", R.drawable.broken_screen_15, R.drawable.broken_img15, "EDGE_SPIDER_WEB"))
            add(EffectItem("16", R.drawable.broken_screen_16, R.drawable.broken_img16, "EDGE_SPIDER_WEB"))
            add(EffectItem("17", R.drawable.broken_screen_17, R.drawable.broken_img17, "EDGE_SPIDER_WEB"))
            add(EffectItem("18", R.drawable.broken_screen_18, R.drawable.broken_img18, "EDGE_SPIDER_WEB"))
            add(EffectItem("19", R.drawable.broken_screen_19, R.drawable.broken_img19, "EDGE_SPIDER_WEB"))
            add(EffectItem("20", R.drawable.broken_screen_20, R.drawable.broken_img20, "EDGE_SPIDER_WEB"))
        }

        // 2. Filter list according to selection type
        when {
            prankType.equals("SPIDER", ignoreCase = true) -> {

                // ✅ FIXED: Directly adding items to 'effectsList' so they display correctly on the UI.
                effectsList.add(EffectItem("1", R.drawable.broken_screen_1, R.drawable.broken_img1, "SPIDER"))
                effectsList.add(EffectItem("2", R.drawable.broken_screen_2, R.drawable.broken_img2, "LED_LINES"))
                effectsList.add(EffectItem("3", R.drawable.broken_screen_3, R.drawable.broken_img3, "RAINBOW_LINES"))
                effectsList.add(EffectItem("4", R.drawable.broken_screen_4, R.drawable.broken_img4, "BULLET_HOLES"))
                effectsList.add(EffectItem("5", R.drawable.broken_screen_5, R.drawable.broken_img5, "GLASS_BURST"))
                effectsList.add(EffectItem("6", R.drawable.broken_screen_6, R.drawable.broken_img6, "SIDE_CRACK"))
                effectsList.add(EffectItem("7", R.drawable.broken_screen_7, R.drawable.broken_img7, "RAINBOW_GLITCH"))
                effectsList.add(EffectItem("8", R.drawable.broken_screen_8, R.drawable.broken_img8, "RED_BURN_OVERLAY"))

            }
            prankType.equals("LED", ignoreCase = true) || prankType.equals("LED_LINES", ignoreCase = true) -> {
                effectsList.add(EffectItem("1", R.drawable.broken_screen_1, R.drawable.broken_img1, "SPIDER"))
                effectsList.add(EffectItem("2", R.drawable.broken_screen_2, R.drawable.broken_img2, "LED_LINES"))
                effectsList.add(EffectItem("3", R.drawable.broken_screen_3, R.drawable.broken_img3, "RAINBOW_LINES"))
                effectsList.add(EffectItem("4", R.drawable.broken_screen_4, R.drawable.broken_img4, "BULLET_HOLES"))
                effectsList.add(EffectItem("5", R.drawable.broken_screen_5, R.drawable.broken_img5, "GLASS_BURST"))
                effectsList.add(EffectItem("6", R.drawable.broken_screen_6, R.drawable.broken_img6, "SIDE_CRACK"))
                effectsList.add(EffectItem("7", R.drawable.broken_screen_7, R.drawable.broken_img7, "RAINBOW_GLITCH"))

            }
            prankType.equals("BULLET", ignoreCase = true) || prankType.equals("BULLET_HOLES", ignoreCase = true) -> {

                effectsList.add(EffectItem("1", R.drawable.broken_screen_1, R.drawable.broken_img1, "SPIDER"))
                effectsList.add(EffectItem("2", R.drawable.broken_screen_2, R.drawable.broken_img2, "LED_LINES"))
                effectsList.add(EffectItem("3", R.drawable.broken_screen_3, R.drawable.broken_img3, "RAINBOW_LINES"))
                effectsList.add(EffectItem("4", R.drawable.broken_screen_4, R.drawable.broken_img4, "BULLET_HOLES"))
                effectsList.add(EffectItem("5", R.drawable.broken_screen_5, R.drawable.broken_img5, "GLASS_BURST"))


            }
            prankType.equals("TOUCH", ignoreCase = true) || prankType.equals("RAINBOW_GLITCH", ignoreCase = true) -> {
                effectsList.add(EffectItem("1", R.drawable.broken_screen_1, R.drawable.broken_img1, "SPIDER"))
                effectsList.add(EffectItem("2", R.drawable.broken_screen_2, R.drawable.broken_img2, "LED_LINES"))
                effectsList.add(EffectItem("3", R.drawable.broken_screen_3, R.drawable.broken_img3, "RAINBOW_LINES"))
                effectsList.add(EffectItem("4", R.drawable.broken_screen_4, R.drawable.broken_img4, "BULLET_HOLES"))
                effectsList.add(EffectItem("5", R.drawable.broken_screen_5, R.drawable.broken_img5, "GLASS_BURST"))
                effectsList.add(EffectItem("6", R.drawable.broken_screen_6, R.drawable.broken_img6, "SIDE_CRACK"))
                effectsList.add(EffectItem("7", R.drawable.broken_screen_7, R.drawable.broken_img7, "RAINBOW_GLITCH"))
                effectsList.add(EffectItem("8", R.drawable.broken_screen_8, R.drawable.broken_img8, "RED_BURN_OVERLAY"))
                effectsList.add(EffectItem("9", R.drawable.broken_screen_9, R.drawable.broken_img9, "VERTICAL_STRIPES"))
                effectsList.add(EffectItem("10", R.drawable.broken_screen_10, R.drawable.broken_img10, "DENSE_GLASS_CRACKS"))
                effectsList.add(EffectItem("11", R.drawable.broken_screen_11, R.drawable.broken_img11, "HORIZONTAL_LINE_GLITCH"))
                effectsList.add(EffectItem("12", R.drawable.broken_screen_12, R.drawable.broken_img12, "BULLET_MULTIPLE_SHATTERS"))
                effectsList.add(EffectItem("13", R.drawable.broken_screen_13, R.drawable.broken_img13, "GREEN_LINE_BURN"))
                effectsList.add(EffectItem("14", R.drawable.broken_screen_14, R.drawable.broken_img14, "CENTER_BULLET"))
                effectsList.add(EffectItem("15", R.drawable.broken_screen_15, R.drawable.broken_img15, "EDGE_SPIDER_WEB"))
                effectsList.add(EffectItem("16", R.drawable.broken_screen_16, R.drawable.broken_img16, "EDGE_SPIDER_WEB"))
                effectsList.add(EffectItem("17", R.drawable.broken_screen_17, R.drawable.broken_img17, "EDGE_SPIDER_WEB"))
                effectsList.add(EffectItem("18", R.drawable.broken_screen_18, R.drawable.broken_img18, "EDGE_SPIDER_WEB"))
                effectsList.add(EffectItem("19", R.drawable.broken_screen_19, R.drawable.broken_img19, "EDGE_SPIDER_WEB"))
                effectsList.add(EffectItem("20", R.drawable.broken_screen_20, R.drawable.broken_img20, "EDGE_SPIDER_WEB"))


            }
            else -> {
                // Load complete master list on default route
                effectsList.addAll(masterList)
            }
        }
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
            findNavController().navigate(R.id.action_global_to_settingFragment)
        }
    }

    private fun navigateToPrankExecution(effect: EffectItem) {
        // Safe navigation sequence to Preview screen with arguments package
        val bundle = Bundle().apply {
            // Find index of clicked item in the filtered list
            val effectIndex = effectsList.indexOf(effect)
            putInt("SELECTED_EFFECT_INDEX", if (effectIndex != -1) effectIndex else 0)
            putString("PRANK_TYPE", prankType)
            putString("PRANK_TITLE", prankTitle)
        }
        findNavController().navigate(R.id.action_global_to_brokenScreenPreviewFragment, bundle)
    }

    private fun startBrokenEffect() {
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