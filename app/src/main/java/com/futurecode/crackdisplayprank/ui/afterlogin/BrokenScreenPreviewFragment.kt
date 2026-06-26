package com.futurecode.crackdisplayprank.ui.afterlogin

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.adapter.CarouselPreviewAdapter
import com.futurecode.crackdisplayprank.ads.interstitial_ad.FullScreenAdsHelper
import com.futurecode.crackdisplayprank.ads.native_ad.NativeAdsHelper
import com.futurecode.crackdisplayprank.base.BaseFragment
import com.futurecode.crackdisplayprank.databinding.FragmentBrokenScreenPreviewBinding
import com.futurecode.crackdisplayprank.model.EffectItem
import com.futurecode.crackdisplayprank.utils.BrokenScreenService
import com.futurecode.crackdisplayprank.utils.CarouselLayoutManager
import com.futurecode.crackdisplayprank.utils.Utils.setAdClickListener
import com.futurecode.crackdisplayprank.viewModel.PrankConfigViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BrokenScreenPreviewFragment : BaseFragment<FragmentBrokenScreenPreviewBinding>(FragmentBrokenScreenPreviewBinding::inflate) {

    private val viewModel: PrankConfigViewModel by viewModels()
    private lateinit var carouselAdapter: CarouselPreviewAdapter
    private val brokenScreenList = ArrayList<EffectItem>()
    private val snapHelper = LinearSnapHelper()

    private var selectedMethod = "TOUCH"
    private var selectedDelay = "OFF"
    private var selectedEffectIndex = 0 // Tracks currently focused center effect

    private var prankType: String = ""
    private var prankTitle: String = ""

    private lateinit var nativeAdsHelper: NativeAdsHelper
    lateinit var fullScreenAdsHelper: FullScreenAdsHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nativeAdsHelper= NativeAdsHelper(requireActivity())
        fullScreenAdsHelper= FullScreenAdsHelper(requireActivity())

        // 1. Safe extraction with default fallback values to prevent NullPointerExceptions
        prankType = arguments?.getString("PRANK_TYPE") ?: "DEFAULT"
        prankTitle = arguments?.getString("PRANK_TITLE") ?: "Broken Screen"

        val title = when (prankType) {
            "SPIDER" -> getString(R.string.spider_crack)
            "TOUCH" -> getString(R.string.touch)
            "BULLET" -> getString(R.string.bullet_effects)
            "LED" -> getString(R.string.led_effects)
            else -> getString(R.string.broken_screen)
        }

        initCarouselData()
        setupCarouselRecyclerView()
        setupClickListeners()
        observeViewModelStates()

        nativeAdsHelper= NativeAdsHelper(requireActivity())
        fullScreenAdsHelper= FullScreenAdsHelper(requireActivity())

        loadNativeAds()

        binding.tvHeaderTitle.text=title

    }

    private fun initCarouselData() {
        brokenScreenList.clear()
        brokenScreenList.add(EffectItem("1", R.drawable.broken_screen_1, R.drawable.broken_img1, "SPIDER"))
        brokenScreenList.add(EffectItem("2", R.drawable.broken_screen_2, R.drawable.broken_img2, "LED_LINES"))
        brokenScreenList.add(EffectItem("3", R.drawable.broken_screen_3, R.drawable.broken_img3, "RAINBOW_LINES"))
        brokenScreenList.add(EffectItem("4", R.drawable.broken_screen_4, R.drawable.broken_img4, "BULLET_HOLES"))
        brokenScreenList.add(EffectItem("5", R.drawable.broken_screen_5, R.drawable.broken_img5, "GLASS_BURST"))
        brokenScreenList.add(EffectItem("6", R.drawable.broken_screen_6, R.drawable.broken_img6, "SIDE_CRACK"))
        brokenScreenList.add(EffectItem("7", R.drawable.broken_screen_7, R.drawable.broken_img7, "RAINBOW_GLITCH"))
        brokenScreenList.add(EffectItem("8", R.drawable.broken_screen_8, R.drawable.broken_img8, "RED_BURN_OVERLAY"))
        brokenScreenList.add(EffectItem("9", R.drawable.broken_screen_9, R.drawable.broken_img9, "VERTICAL_STRIPES"))
        brokenScreenList.add(EffectItem("10", R.drawable.broken_screen_10, R.drawable.broken_img10, "DENSE_GLASS_CRACKS"))
        brokenScreenList.add(EffectItem("11", R.drawable.broken_screen_11, R.drawable.broken_img11, "HORIZONTAL_LINE_GLITCH"))
        brokenScreenList.add(EffectItem("12", R.drawable.broken_screen_12, R.drawable.broken_img12, "BULLET_MULTIPLE_SHATTERS"))
        brokenScreenList.add(EffectItem("13", R.drawable.broken_screen_13, R.drawable.broken_img13, "GREEN_LINE_BURN"))
        brokenScreenList.add(EffectItem("14", R.drawable.broken_screen_14, R.drawable.broken_img14, "CENTER_BULLET"))
        brokenScreenList.add(EffectItem("15", R.drawable.broken_screen_15, R.drawable.broken_img15, "EDGE_SPIDER_WEB"))
        brokenScreenList.add(EffectItem("16", R.drawable.broken_screen_16, R.drawable.broken_img16, "EDGE_SPIDER_WEB"))
        brokenScreenList.add(EffectItem("17", R.drawable.broken_screen_17, R.drawable.broken_img17, "EDGE_SPIDER_WEB"))
        brokenScreenList.add(EffectItem("18", R.drawable.broken_screen_18, R.drawable.broken_img18, "EDGE_SPIDER_WEB"))
        brokenScreenList.add(EffectItem("19", R.drawable.broken_screen_19, R.drawable.broken_img19, "EDGE_SPIDER_WEB"))
        brokenScreenList.add(EffectItem("20", R.drawable.broken_screen_20, R.drawable.broken_img20, "EDGE_SPIDER_WEB"))

    }

    private fun setupCarouselRecyclerView() {
        // ✅ 1. Safe argument retrieval from click navigation bundle
        val initialIndex = arguments?.getInt("SELECTED_EFFECT_INDEX") ?: 0
        selectedEffectIndex = initialIndex
        viewModel.setSelectedEffect(initialIndex)

        carouselAdapter = CarouselPreviewAdapter(
            items = brokenScreenList,
            onItemClick = { position ->
                binding.rvView.smoothScrollToPosition(position)
            },
            onPreviewClick = { position ->
                // ✅ FIXED: Safely retrieve and define 'currentItem' before putting inside transition bundle keys
                if (position in brokenScreenList.indices) {
                    val currentItem = brokenScreenList[position]
                    val bundle = Bundle().apply {
                        putString("PRANK_TYPE", prankType)
                        putString("PRANK_TITLE", prankTitle)
                        putInt("IMAGE", currentItem.backgroundImageResId)
                        putInt("CRACK", currentItem.crackImageResId)
                    }
                    findNavController().navigate(
                        R.id.action_global_to_previewScreenFragment,
                        bundle
                    )
                }
            }
        )

        val customLayoutManager = CarouselLayoutManager(requireContext())
        binding.rvView.layoutManager = customLayoutManager
        binding.rvView.adapter = carouselAdapter

        // Compile-time safe sdp dynamic padding calculation to prevent unresolved reference build crashes
        binding.rvView.post {
            val rvWidth = binding.rvView.width
            val sdpId = resources.getIdentifier("_110sdp", "dimen", requireContext().packageName)

            val itemWidth = if (sdpId != 0) {
                resources.getDimensionPixelSize(sdpId)
            } else {
                (110 * resources.displayMetrics.density).toInt()
            }

            val sidePadding = (rvWidth - itemWidth) / 2
            binding.rvView.setPadding(sidePadding, 0, sidePadding, 0)

            // ✅ 2. Scroll directly to the clicked index instead of static 0
            binding.rvView.scrollToPosition(initialIndex)

            // ✅ 3. Update the header title text with the selected item's category
            if (initialIndex in brokenScreenList.indices) {
               // binding.tvHeaderTitle.text = brokenScreenList[initialIndex].styleCategory
            }
        }

        snapHelper.attachToRecyclerView(binding.rvView)

        binding.rvView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(customLayoutManager)
                    if (centerView != null) {
                        val position = customLayoutManager.getPosition(centerView)
                        if (position in brokenScreenList.indices) {
                           // binding.tvHeaderTitle.text = brokenScreenList[position].styleCategory
                            selectedEffectIndex = position
                            viewModel.setSelectedEffect(position)
                        }
                    }
                }
            }
        })
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnHelpInfo.setAdClickListener(requireActivity(), fullScreenAdsHelper) {
            findNavController().navigate(R.id.action_global_to_howToUseFragment)
        }

        // Method configurations
        binding.clMethodTouch.setOnClickListener { viewModel.setActivationMethod("TOUCH") }
        binding.clMethodShake.setOnClickListener { viewModel.setActivationMethod("SHAKE") }
        binding.clMethodTimer.setOnClickListener { viewModel.setActivationMethod("TIMER") }

        // Timer option tags
        binding.tvTimeOff.setOnClickListener { viewModel.setTimerDelay("OFF") }
        binding.tvTime10s.setOnClickListener {  viewModel.setTimerDelay("10s") }
        binding.tvTime15s.setOnClickListener { viewModel.setTimerDelay("15s") }
        binding.tvTime20s.setOnClickListener { viewModel.setTimerDelay("20s") }
        binding.tvTime30s.setOnClickListener { viewModel.setTimerDelay("30s") }
        binding.tvTime1m.setOnClickListener { viewModel.setTimerDelay("1m") }
        binding.tvTime2m.setOnClickListener { viewModel.setTimerDelay("2m") }

        // ✅ FIXED: setAdClickListener Integration with correct qualified return labels
        binding.btnStartPrank.setAdClickListener(requireActivity(), fullScreenAdsHelper) {
            // Draw Overlays system permission verification
            if (!Settings.canDrawOverlays(requireContext())) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${requireContext().packageName}")
                    )
                )
                return@setAdClickListener // ✅ CORRECTED: Label matches function signature scope
            }

            if (selectedMethod == "TIMER" && selectedDelay == "OFF") {
                //Toast.makeText(requireContext(), "Please select a timer delay first!", Toast.LENGTH_SHORT).show()
                return@setAdClickListener // ✅ CORRECTED: Label matches function signature scope
            }

            // Launch safe foreground task delegation
            startBackgroundPrankService()
        }
    }

    private fun startBackgroundPrankService() {
        val currentItem = brokenScreenList[selectedEffectIndex]
        val serviceIntent = Intent(requireContext(), BrokenScreenService::class.java).apply {
            putExtra(BrokenScreenService.EXTRA_BACKGROUND, currentItem.crackImageResId)
            putExtra(BrokenScreenService.EXTRA_CRACK, currentItem.crackImageResId)

            when (selectedMethod) {
                "TOUCH" -> {
                    putExtra(BrokenScreenService.EXTRA_TOUCH_CATCHER, true)
                }
                "SHAKE" -> {
                    putExtra(BrokenScreenService.EXTRA_SHAKE_TRIGGER, true)
                }
                "TIMER" -> {
                    putExtra(BrokenScreenService.EXTRA_TIMER_TRIGGER, true)
                    val delayMillis = when (selectedDelay) {
                        "10s" -> 10000L
                        "15s" -> 15000L
                        "20s" -> 20000L
                        "30s" -> 30000L
                        "1m" -> 60000L
                        "2m" -> 120000L
                        else -> 10000L
                    }
                    putExtra(BrokenScreenService.EXTRA_TIMER_DELAY, delayMillis)
                }
            }
        }

        // 1. Launchforeground execution process safely
        ContextCompat.startForegroundService(requireContext(), serviceIntent)

        // 2. Instantly minimize/hide the app workspace window cleanly
        Handler(Looper.getMainLooper()).postDelayed({
            requireActivity().moveTaskToBack(true)
        }, 300)

        // Inform user with dynamic armed message matching selected method
        val armMessage = when (selectedMethod) {
            "TOUCH" -> "Touch Trigger Armed! Tap screen to break it."
            "SHAKE" -> "Shake Trigger Armed! Shake device to break it."
            "TIMER" -> "Timer Armed! Glass will crack in $selectedDelay."
            else -> "Prank armed!"
        }
       // Toast.makeText(requireContext(), armMessage, Toast.LENGTH_SHORT).show()
    }

    private fun observeViewModelStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Monitor selected activation methods
                launch {
                    viewModel.activationMethod.collectLatest { method ->
                        selectedMethod = method
                        resetMethodCards()
                        val neonBlue = ContextCompat.getColor(requireContext(), R.color.lang_cyan_glow)

                        when (method) {
                            "TOUCH" -> {
                                binding.clMethodTouch.setBackgroundResource(R.drawable.bg_activation_card_selected)
                                binding.ivTouchIcon.imageTintList = ColorStateList.valueOf(neonBlue)
                                binding.ivTouchCheck.visibility = View.VISIBLE
                                binding.vTouchCircle.visibility = View.GONE
                                binding.hsvTimerOptions.visibility = View.GONE
                            }

                            "SHAKE" -> {
                                binding.clMethodShake.setBackgroundResource(R.drawable.bg_activation_card_selected)
                                binding.ivShakeIcon.imageTintList = ColorStateList.valueOf(neonBlue)
                                binding.ivShakeCheck.visibility = View.VISIBLE
                                binding.vShakeCircle.visibility = View.GONE
                                binding.hsvTimerOptions.visibility = View.GONE
                            }

                            "TIMER" -> {
                                binding.clMethodTimer.setBackgroundResource(R.drawable.bg_activation_card_selected)
                                binding.ivTimerIcon.imageTintList = ColorStateList.valueOf(neonBlue)
                                binding.ivTimerCheck.visibility = View.VISIBLE
                                binding.vTimerCircle.visibility = View.GONE
                                binding.hsvTimerOptions.visibility = View.VISIBLE
                            }
                        }
                    }
                }

                // Monitor active timer delays
                launch {
                    viewModel.timerDelay.collectLatest { activeDelay ->
                        selectedDelay = activeDelay
                        resetTimerTags()
                        val activeTag = when (activeDelay) {
                            "OFF" -> binding.tvTimeOff
                            "10s" -> binding.tvTime10s
                            "15s" -> binding.tvTime15s
                            "20s" -> binding.tvTime20s
                            "30s" -> binding.tvTime30s
                            "1m" -> binding.tvTime1m
                            "2m" -> binding.tvTime2m
                            else -> binding.tvTime10s
                        }
                        activeTag.setBackgroundResource(R.drawable.bg_timer_tag_selected)
                    }
                }
            }
        }
    }

    private fun resetMethodCards() {
        val textSecondary = ContextCompat.getColor(requireContext(), R.color.lang_text_muted)

        binding.clMethodTouch.setBackgroundResource(R.drawable.bg_activation_card_unselected)
        binding.clMethodShake.setBackgroundResource(R.drawable.bg_activation_card_unselected)
        binding.clMethodTimer.setBackgroundResource(R.drawable.bg_activation_card_unselected)

        binding.ivTouchIcon.imageTintList = ColorStateList.valueOf(textSecondary)
        binding.ivShakeIcon.imageTintList = ColorStateList.valueOf(textSecondary)
        binding.ivTimerIcon.imageTintList = ColorStateList.valueOf(textSecondary)

        binding.ivTouchCheck.visibility = View.GONE
        binding.ivShakeCheck.visibility = View.GONE
        binding.ivTimerCheck.visibility = View.GONE

        binding.vTouchCircle.visibility = View.VISIBLE
        binding.vShakeCircle.visibility = View.VISIBLE
        binding.vTimerCircle.visibility = View.VISIBLE
    }

    private fun resetTimerTags() {
        val timerViews = listOf(
            binding.tvTimeOff, binding.tvTime10s, binding.tvTime15s,
            binding.tvTime20s, binding.tvTime30s, binding.tvTime1m, binding.tvTime2m
        )
        timerViews.forEach { view ->
            view.setBackgroundResource(R.drawable.bg_timer_tag_unselected)
        }
    }

    fun loadNativeAds(){
        nativeAdsHelper = NativeAdsHelper(requireActivity())
        nativeAdsHelper?.showNativeAd(
            nativeBannerAdView = binding.nativeAds3.frame,
            mainLayout = binding.nativeAds3.mainLayout,
            placeholder = binding.nativeAds3.placeholder
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}