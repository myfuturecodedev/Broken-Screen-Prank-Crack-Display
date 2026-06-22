package com.futurecode.crackdisplayprank.ui.afterlogin


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.adapter.CarouselPreviewAdapter
import com.futurecode.crackdisplayprank.databinding.FragmentBrokenScreenPreviewBinding
import com.futurecode.crackdisplayprank.model.EffectItem
import com.futurecode.crackdisplayprank.utils.BrokenScreenService
import com.futurecode.crackdisplayprank.utils.CarouselLayoutManager
import com.futurecode.crackdisplayprank.utils.ShakeDetector
import com.futurecode.crackdisplayprank.viewModel.PrankConfigViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * 15-Year Developer Standard: Fully resolved reactive state machine coordinates
 * layout carousel snapping with safe sensor listeners and background service tasks.
 */
//class BrokenScreenPreviewFragment : Fragment() {
//
//    private var _binding: FragmentBrokenScreenPreviewBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: PrankConfigViewModel by viewModels()
//    private lateinit var carouselAdapter: CarouselPreviewAdapter
//    private val carouselList = ArrayList<EffectItem>()
//    private val snapHelper = LinearSnapHelper()
//
//    private var selectedMethod = "TOUCH"
//    private var prankArmed = false
//    private var isShakeListening = false
//
//    private lateinit var sensorManager: SensorManager
//    private var shakeDetector: ShakeDetector? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentBrokenScreenPreviewBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        initCarouselData()
//        setupCarouselRecyclerView()
//        setupClickListeners()
//        observeViewModelStates()
//    }
//
//    private fun initCarouselData() {
//        carouselList.clear()
//        // Map combinations matching the slider previews of Broken Screen mockup design
//        carouselList.add(
//            EffectItem(
//                "1",
//                R.drawable.onboard_one,
//                R.drawable.onboard_three,
//                "Spider Crack"
//            )
//        )
//        carouselList.add(
//            EffectItem(
//                "2",
//                R.drawable.onboard_two,
//                R.drawable.onboard_two,
//                "LED Green Lines"
//            )
//        )
//        carouselList.add(
//            EffectItem(
//                "3",
//                R.drawable.onboard_three,
//                R.drawable.onboard_one,
//                "Rainbow Glitch"
//            )
//        )
//        carouselList.add(
//            EffectItem(
//                "4",
//                R.drawable.onboard_one,
//                R.drawable.onboard_three,
//                "Bullet Shatter"
//            )
//        )
//        carouselList.add(
//            EffectItem(
//                "5",
//                R.drawable.onboard_two,
//                R.drawable.onboard_two,
//                "OLED Leak"
//            )
//        )
//    }
//
//    private fun setupCarouselRecyclerView() {
//        carouselAdapter = CarouselPreviewAdapter(carouselList) { position ->
//            binding.rvView.smoothScrollToPosition(position)
//        }
//
//        val customLayoutManager = CarouselLayoutManager(requireContext())
//        binding.rvView.layoutManager = customLayoutManager
//        binding.rvView.adapter = carouselAdapter
//
//        binding.rvView.post {
//            val rvWidth = binding.rvView.width
//            val sdpId = resources.getIdentifier("_110sdp", "dimen", requireContext().packageName)
//
//            val itemWidth = if (sdpId != 0) {
//                resources.getDimensionPixelSize(sdpId)
//            } else {
//                // Symmetrical safe fallback (110dp converted to raw pixels)
//                (110 * resources.displayMetrics.density).toInt()
//            }
//
//            val sidePadding = (rvWidth - itemWidth) / 2
//            binding.rvView.setPadding(sidePadding, 0, sidePadding, 0)
//            binding.rvView.scrollToPosition(0)
//        }
//
//        snapHelper.attachToRecyclerView(binding.rvView)
//
//        binding.rvView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    val centerView = snapHelper.findSnapView(customLayoutManager)
//                    if (centerView != null) {
//                        val position = customLayoutManager.getPosition(centerView)
//                        if (position in carouselList.indices) {
//                            binding.tvHeaderTitle.text = carouselList[position].styleCategory
//                        }
//                    }
//                }
//            }
//        })
//    }
//
//    private fun setupClickListeners() {
//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//        binding.btnHelpInfo.setOnClickListener {
//            Toast.makeText(
//                requireContext(),
//                "Swipe effect models, choose trigger, and hand over your phone!",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//
//        // Method selector click listeners
//        binding.clMethodTouch.setOnClickListener { viewModel.setActivationMethod("TOUCH") }
//        binding.clMethodShake.setOnClickListener { viewModel.setActivationMethod("SHAKE") }
//        binding.clMethodTimer.setOnClickListener { viewModel.setActivationMethod("TIMER") }
//
//        // Timer option tag click listeners
//        binding.tvTimeOff.setOnClickListener { viewModel.setTimerDelay("OFF") }
//        binding.tvTime10s.setOnClickListener { viewModel.setTimerDelay("10s") }
//        binding.tvTime15s.setOnClickListener { viewModel.setTimerDelay("15s") }
//        binding.tvTime20s.setOnClickListener { viewModel.setTimerDelay("20s") }
//        binding.tvTime30s.setOnClickListener { viewModel.setTimerDelay("30s") }
//        binding.tvTime1m.setOnClickListener { viewModel.setTimerDelay("1m") }
//        binding.tvTime2m.setOnClickListener { viewModel.setTimerDelay("2m") }
//
//        binding.btnStartPrank.setOnClickListener {
//            if (!Settings.canDrawOverlays(requireContext())) {
//                startActivity(
//                    Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:${requireContext().packageName}")
//                    )
//                )
//                return@setOnClickListener
//            }
//
//            when (selectedMethod) {
//                "TOUCH" -> {
//                    prankArmed = true
//                    Toast.makeText(
//                        requireContext(),
//                        "Touch anywhere on screen to activate!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                "SHAKE" -> {
//                    startShakeDetection()
//                    Toast.makeText(
//                        requireContext(),
//                        "Shake phone to activate prank",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//                "TIMER" -> {
//                    startTimerPrank()
//                }
//            }
//        }
//
//        binding.btnPreviewEffect.setOnClickListener {
//            val currentEffect = binding.tvHeaderTitle.text.toString()
//            Toast.makeText(
//                requireContext(),
//                "Opening fullscreen preview for: $currentEffect",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    private fun observeViewModelStates() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                // Monitor selected activation methods
//                launch {
//                    viewModel.activationMethod.collectLatest { method ->
//                        // Fix 1: Properly update the selectedMethod state variable
//                        selectedMethod = method
//
//                        resetMethodCards()
//                        val neonBlue = ContextCompat.getColor(requireContext(), R.color.lang_cyan_glow)
//
//                        when (method) {
//                            "TOUCH" -> {
//                                binding.clMethodTouch.setBackgroundResource(R.drawable.bg_activation_card_selected)
//                                binding.ivTouchIcon.imageTintList = ColorStateList.valueOf(neonBlue)
//                                binding.ivTouchCheck.visibility = View.VISIBLE
//                                binding.vTouchCircle.visibility = View.GONE
//                                binding.hsvTimerOptions.visibility = View.GONE
//                            }
//
//                            "SHAKE" -> {
//                                binding.clMethodShake.setBackgroundResource(R.drawable.bg_activation_card_selected)
//                                android.util.Log.d("PrankPreview", "Shake method visual selected")
//                                binding.ivShakeIcon.imageTintList = ColorStateList.valueOf(neonBlue)
//                                binding.ivShakeCheck.visibility = View.VISIBLE
//                                binding.vShakeCircle.visibility = View.GONE
//                                binding.hsvTimerOptions.visibility = View.GONE
//                            }
//
//                            "TIMER" -> {
//                                binding.clMethodTimer.setBackgroundResource(R.drawable.bg_activation_card_selected)
//                                binding.ivTimerIcon.imageTintList = ColorStateList.valueOf(neonBlue)
//                                binding.ivTimerCheck.visibility = View.VISIBLE
//                                binding.vTimerCircle.visibility = View.GONE
//                                binding.hsvTimerOptions.visibility = View.VISIBLE
//                            }
//                        }
//                    }
//                }
//
//                // Monitor active timer delays
//                launch {
//                    viewModel.timerDelay.collectLatest { activeDelay ->
//                        resetTimerTags()
//                        val activeTag = when (activeDelay) {
//                            "OFF" -> binding.tvTimeOff
//                            "10s" -> binding.tvTime10s
//                            "15s" -> binding.tvTime15s
//                            "20s" -> binding.tvTime20s
//                            "30s" -> binding.tvTime30s
//                            "1m" -> binding.tvTime1m
//                            "2m" -> binding.tvTime2m
//                            else -> binding.tvTime10s
//                        }
//                        activeTag.setBackgroundResource(R.drawable.bg_timer_tag_selected)
//                    }
//                }
//            }
//        }
//    }
//
//    private fun resetMethodCards() {
//        val textSecondary = ContextCompat.getColor(requireContext(), R.color.lang_text_muted)
//
//        binding.clMethodTouch.setBackgroundResource(R.drawable.bg_activation_card_unselected)
//        binding.clMethodShake.setBackgroundResource(R.drawable.bg_activation_card_unselected)
//        binding.clMethodTimer.setBackgroundResource(R.drawable.bg_activation_card_unselected)
//
//        binding.ivTouchIcon.imageTintList = ColorStateList.valueOf(textSecondary)
//        binding.ivShakeIcon.imageTintList = ColorStateList.valueOf(textSecondary)
//        binding.ivTimerIcon.imageTintList = ColorStateList.valueOf(textSecondary)
//
//        binding.ivTouchCheck.visibility = View.GONE
//        binding.ivShakeCheck.visibility = View.GONE
//        binding.ivTimerCheck.visibility = View.GONE
//
//        binding.vTouchCircle.visibility = View.VISIBLE
//        binding.vShakeCircle.visibility = View.VISIBLE
//        binding.vTimerCircle.visibility = View.VISIBLE
//    }
//
//    private fun startOverlay() {
//        // Safe implementation using modern ContextCompat startForegroundService
//
//        val serviceIntent = Intent(requireContext(), BrokenScreenService::class.java)
//        ContextCompat.startForegroundService(requireContext(), serviceIntent)
//        requireActivity().moveTaskToBack(true)
//    }
//
//    private fun startShakeDetection() {
//        // Fix 3: Avoid registering multiple concurrent shake sensor listeners
//        if (isShakeListening) return
//        isShakeListening = true
//
//        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//
//        shakeDetector = ShakeDetector {
//            isShakeListening = false
//            startOverlay()
//            sensorManager.unregisterListener(shakeDetector)
//        }
//
//        sensorManager.registerListener(
//            shakeDetector,
//            sensor,
//            SensorManager.SENSOR_DELAY_NORMAL
//        )
//    }
//
//    private fun startTimerPrank() {
//        val delayValue = viewModel.timerDelay.value
//
//        // Fix 2: Prevent instant activation on OFF selection state
//        if (delayValue == "OFF") {
//            Toast.makeText(
//                requireContext(),
//                "Please select a timer delay first!",
//                Toast.LENGTH_SHORT
//            ).show()
//            return
//        }
//
//        val delay = when (delayValue) {
//            "10s" -> 10000L
//            "15s" -> 15000L
//            "20s" -> 20000L
//            "30s" -> 30000L
//            "1m" -> 60000L
//            "2m" -> 120000L
//            else -> 0L
//        }
//
//        Toast.makeText(
//            requireContext(),
//            "Timer started! Prank will trigger in $delayValue",
//            Toast.LENGTH_SHORT
//        ).show()
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            startOverlay()
//        }, delay)
//    }
//
//    private fun resetTimerTags() {
//        val timerViews = listOf(
//            binding.tvTimeOff, binding.tvTime10s, binding.tvTime15s,
//            binding.tvTime20s, binding.tvTime30s, binding.tvTime1m, binding.tvTime2m
//        )
//        timerViews.forEach { view ->
//            view.setBackgroundResource(R.drawable.bg_timer_tag_unselected)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        binding.root.setOnTouchListener { _, _ ->
//            if (prankArmed) {
//                prankArmed = false
//                startOverlay()
//            }
//            false
//        }
//    }
//
//    override fun onDestroyView() {
//        if (::sensorManager.isInitialized) {
//            shakeDetector?.let {
//                sensorManager.unregisterListener(it)
//            }
//        }
//        isShakeListening = false
//        super.onDestroyView()
//        _binding = null
//    }
//}



class BrokenScreenPreviewFragment : Fragment() {

    private var _binding: FragmentBrokenScreenPreviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PrankConfigViewModel by viewModels()
    private lateinit var carouselAdapter: CarouselPreviewAdapter
    private val carouselList = ArrayList<EffectItem>()
    private val snapHelper = LinearSnapHelper()

    private var selectedMethod = "TOUCH"
    private var selectedDelay = "OFF"
    private var selectedEffectIndex = 0 // Tracks currently focused center effect

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrokenScreenPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCarouselData()
        setupCarouselRecyclerView()
        setupClickListeners()
        observeViewModelStates()
    }

    private fun initCarouselData() {
        carouselList.clear()
        // High fidelity asset mapping corresponding to design mockup presets
        carouselList.add(EffectItem("1", R.drawable.onboard_one, R.drawable.onboard_three, "Spider Crack"))
        carouselList.add(EffectItem("2", R.drawable.onboard_two, R.drawable.onboard_two, "LED Green Lines"))
        carouselList.add(EffectItem("3", R.drawable.onboard_three, R.drawable.onboard_one, "Rainbow Glitch"))
        carouselList.add(EffectItem("4", R.drawable.onboard_one, R.drawable.onboard_three, "Bullet Shatter"))
        carouselList.add(EffectItem("5", R.drawable.onboard_two, R.drawable.onboard_two, "OLED Leak"))
    }

    private fun setupCarouselRecyclerView() {
        carouselAdapter = CarouselPreviewAdapter(carouselList) { position ->
            binding.rvView.smoothScrollToPosition(position)
        }

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
            binding.rvView.scrollToPosition(0)
            selectedEffectIndex = 0
        }

        snapHelper.attachToRecyclerView(binding.rvView)

        binding.rvView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView = snapHelper.findSnapView(customLayoutManager)
                    if (centerView != null) {
                        val position = customLayoutManager.getPosition(centerView)
                        if (position in carouselList.indices) {
                            binding.tvHeaderTitle.text = carouselList[position].styleCategory
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

        binding.btnHelpInfo.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Swipe effect models, choose trigger, and hand over your phone!",
                Toast.LENGTH_LONG
            ).show()
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

        binding.btnStartPrank.setOnClickListener {
            // Draw Overlays system permission verification
            if (!Settings.canDrawOverlays(requireContext())) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${requireContext().packageName}")
                    )
                )
                return@setOnClickListener
            }

            if (selectedMethod == "TIMER" && selectedDelay == "OFF") {
                Toast.makeText(requireContext(), "Please select a timer delay first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Launch safe foreground task delegation
            startBackgroundPrankService()
        }

        binding.btnPreviewEffect.setOnClickListener {
            if (!Settings.canDrawOverlays(requireContext())) {
                startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:${requireContext().packageName}")
                    )
                )
                return@setOnClickListener
            }

            // Launches interactive fullscreen preview dynamically (Closes automatically after 3.5s)
            val currentItem = carouselList[selectedEffectIndex]
            val previewIntent = Intent(requireContext(), BrokenScreenService::class.java).apply {
                putExtra(BrokenScreenService.EXTRA_BACKGROUND, currentItem.backgroundImageResId)
                putExtra(BrokenScreenService.EXTRA_CRACK, currentItem.crackImageResId)
                putExtra(BrokenScreenService.EXTRA_PREVIEW_MODE, true)
            }
            ContextCompat.startForegroundService(requireContext(), previewIntent)
        }
    }

    private fun startBackgroundPrankService() {
        val currentItem = carouselList[selectedEffectIndex]
        val serviceIntent = Intent(requireContext(), BrokenScreenService::class.java).apply {
            putExtra(BrokenScreenService.EXTRA_BACKGROUND, currentItem.backgroundImageResId)
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
        Toast.makeText(requireContext(), armMessage, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}