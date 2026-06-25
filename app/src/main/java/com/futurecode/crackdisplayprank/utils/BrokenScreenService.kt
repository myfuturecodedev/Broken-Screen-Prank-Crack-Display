package com.futurecode.crackdisplayprank.utils

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.futurecode.crackdisplayprank.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

import android.os.Build
import android.view.Gravity

import android.widget.ImageView
import androidx.core.app.NotificationCompat
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlin.math.sqrt


/**
 * 15-Year Developer Standard: High-fidelity WindowManager service overlay
 * supporting all Android versions up to Android 15 (FGS Special Use compliant).
 * Supports global invisible touch catcher, hardware shake sensor, and background timers.
 * * ✅ UPDATED FEATURE: Plays dynamic R.raw.sound_broken audio effect safely
 * right before displaying the screen crack overlay to maximize impact!
 */



//class BrokenScreenService : Service(), SensorEventListener {
//
//    private lateinit var windowManager: WindowManager
//    private var sensorManager: SensorManager? = null
//    private var accelerometer: Sensor? = null
//
//    private var touchCatcherOverlay: View? = null
//    private var activeCrackOverlay: View? = null
//
//    // Background handlers to completely prevent memory leaks
//    private val backgroundHandler = Handler(Looper.getMainLooper())
//    private var timerRunnable: Runnable? = null
//
//    private var selectedBgRes = R.drawable.broken_screen_2
//    private var selectedCrackRes = R.drawable.broken_screen_1
//    private var isShakeRegistered = false
//
//    companion object {
//        const val EXTRA_BACKGROUND = "EXTRA_BACKGROUND"
//        const val EXTRA_CRACK = "EXTRA_CRACK"
//        const val EXTRA_TOUCH_CATCHER = "EXTRA_TOUCH_CATCHER"
//        const val EXTRA_PREVIEW_MODE = "EXTRA_PREVIEW_MODE"
//
//        // Dynamic addition for Shake & Timer triggers from Fragment
//        const val EXTRA_SHAKE_TRIGGER = "EXTRA_SHAKE_TRIGGER"
//        const val EXTRA_TIMER_TRIGGER = "EXTRA_TIMER_TRIGGER"
//        const val EXTRA_TIMER_DELAY = "EXTRA_TIMER_DELAY"
//
//        private const val CHANNEL_ID = "broken_screen_prank_channel"
//        private const val NOTIFICATION_ID = 4851
//        private const val SHAKE_THRESHOLD = 14.0f // G-force threshold for reliable shake detection
//    }
//
//    override fun onBind(intent: Intent?): IBinder? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        startForeground(NOTIFICATION_ID, createNotification())
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (intent == null) return START_NOT_STICKY
//
//        selectedBgRes = intent.getIntExtra(EXTRA_BACKGROUND, R.drawable.broken_screen_2)
//        selectedCrackRes = intent.getIntExtra(EXTRA_CRACK, R.drawable.broken_screen_1)
//        val isTouchCatcher = intent.getBooleanExtra(EXTRA_TOUCH_CATCHER, false)
//        val isPreview = intent.getBooleanExtra(EXTRA_PREVIEW_MODE, false)
//        val isShakeTrigger = intent.getBooleanExtra(EXTRA_SHAKE_TRIGGER, false)
//        val isTimerTrigger = intent.getBooleanExtra(EXTRA_TIMER_TRIGGER, false)
//
//        // Clear any previous active triggers to prevent overlaps
//        cleanAllBackgroundTriggers()
//
//        when {
//            isPreview -> {
//                // ✅ FIXED: Explicitly pass isPreviewMode = true for preview actions
//                drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = true)
//                // Auto close preview mode after 3.5 seconds
//                backgroundHandler.postDelayed({
//                    stopSelf()
//                }, 3500)
//            }
//            isTouchCatcher -> {
//                setupGlobalTouchCatcher(selectedBgRes, selectedCrackRes)
//            }
//            isShakeTrigger -> {
//                setupBackgroundShakeDetector()
//            }
//            isTimerTrigger -> {
//                val delayMillis = intent.getLongExtra(EXTRA_TIMER_DELAY, 10000L)
//                setupBackgroundTimer(delayMillis)
//            }
//            else -> {
//                // Instantly draw crack if no specific trigger state is passed
//                triggerDeviceVibration()
//                drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = false)
//            }
//        }
//
//        return START_STICKY
//    }
//
//    /**
//     * Creates an invisible full-screen touch interceptor window to catch global gestures.
//     * Triggers crack on very first tap and unregisters itself immediately.
//     */
//    private fun setupGlobalTouchCatcher(bgRes: Int, crackRes: Int) {
//        removeOverlaySafely(touchCatcherOverlay)
//
//        val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        } else {
//            @Suppress("DEPRECATION")
//            WindowManager.LayoutParams.TYPE_PHONE
//        }
//
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT,
//            windowType,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
//                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//            PixelFormat.TRANSLUCENT
//        ).apply {
//            gravity = Gravity.TOP or Gravity.START
//        }
//
//        val catcher = View(this)
//        catcher.setOnTouchListener { _, _ ->
//            // Trigger crack overlay break!
//            triggerDeviceVibration()
//            drawFullscreenCrack(bgRes, crackRes, isPreviewMode = false)
//
//            // Instantly clean up the invisible touch layer so the victim can see the crack underneath
//            cleanTouchCatcher()
//            true
//        }
//
//        try {
//            windowManager.addView(catcher, params)
//            touchCatcherOverlay = catcher
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    /**
//     * SHAKE MODE: Registers hardware Accelerometer to listen for G-force in background.
//     */
//    private fun setupBackgroundShakeDetector() {
//        if (isShakeRegistered) return
//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//
//        accelerometer?.let {
//            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
//            isShakeRegistered = true
//        }
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) return
//
//        val x = event.values[0]
//        val y = event.values[1]
//        val z = event.values[2]
//
//        val gForce = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
//
//        if (gForce > SHAKE_THRESHOLD) {
//            // Unregister sensor first to avoid duplicate fires
//            cleanSensorDetector()
//            triggerDeviceVibration()
//            drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = false)
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
//
//    /**
//     * TIMER MODE: Schedules background runnable to trigger crack after delay.
//     */
//    private fun setupBackgroundTimer(delayMillis: Long) {
//        timerRunnable = Runnable {
//            triggerDeviceVibration()
//            drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = false)
//        }
//        backgroundHandler.postDelayed(timerRunnable!!, delayMillis)
//    }
//
//    /**
//     * ✅ HELPER METHOD: Safely plays the broken screen audio effect from raw resources.
//     * Prevents memory leaks by auto-releasing the MediaPlayer instance after playback.
//     */
//    private fun playCrackSound() {
//        try {
//            val mediaPlayer = MediaPlayer.create(this, R.raw.sound_broken)
//            mediaPlayer?.setOnCompletionListener { mp ->
//                mp.release() // Clean up player memory
//            }
//            mediaPlayer?.start()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    /**
//     * Draws the realistic crack system overlay layer over the screen.
//     */
//    private fun drawFullscreenCrack(bgRes: Int, crackRes: Int, isPreviewMode: Boolean = false) {
//        if (activeCrackOverlay != null) return
//
//        // 🌟 Play the broken screen audio sound right before displaying overlay
//        playCrackSound()
//
//        val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//        } else {
//            @Suppress("DEPRECATION")
//            WindowManager.LayoutParams.TYPE_PHONE
//        }
//
//        // Window flag configurations
//        val windowFlags = if (isPreviewMode) {
//            // Preview mode allows normal touch checking options
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
//                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        } else {
//            // Real Prank Mode forces NOT_TOUCHABLE so it stays permanent
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
//                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//
//        }
//
//        val params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT,
//            windowType,
//            windowFlags,
//            PixelFormat.TRANSLUCENT
//        ).apply {
//            gravity = Gravity.TOP or Gravity.START
//        }
//
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val view = inflater.inflate(R.layout.layout_overlay_crack, null)
//
//        val ivBg = view.findViewById<ImageView>(R.id.ivOverlayBg)
//        val ivCrack = view.findViewById<ImageView>(R.id.ivOverlayCrack)
//
//        ivBg.setImageResource(bgRes)
//        ivCrack.setImageResource(crackRes)
//
//        // Only preview mode allows close-on-tap
//        if (isPreviewMode) {
//            view.setOnClickListener {
//                stopSelf()
//            }
//        }
//
//        try {
//            windowManager.addView(view, params)
//            activeCrackOverlay = view
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    /**
//     * Triggers haptic feedback / vibration to simulate heavy screen impact.
//     */
//    private fun triggerDeviceVibration() {
//        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
//            vibratorManager.defaultVibrator
//        } else {
//            @Suppress("DEPRECATION")
//            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            vibrator.vibrate(VibrationEffect.createOneShot(450, VibrationEffect.DEFAULT_AMPLITUDE))
//        } else {
//            @Suppress("DEPRECATION")
//            vibrator.vibrate(450)
//        }
//    }
//
//    private fun removeOverlaySafely(view: View?) {
//        view?.let {
//            try {
//                windowManager.removeView(it)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun cleanTouchCatcher() {
//        touchCatcherOverlay?.let {
//            removeOverlaySafely(it)
//            touchCatcherOverlay = null
//        }
//    }
//
//    private fun cleanSensorDetector() {
//        if (isShakeRegistered) {
//            sensorManager?.unregisterListener(this)
//            isShakeRegistered = false
//        }
//    }
//
//    private fun cleanAllBackgroundTriggers() {
//        cleanTouchCatcher()
//        cleanSensorDetector()
//        timerRunnable?.let {
//            backgroundHandler.removeCallbacks(it)
//            timerRunnable = null
//        }
//    }
//
//    private fun createNotification(): Notification {
//        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "Prank Service Notification",
//                NotificationManager.IMPORTANCE_LOW
//            )
//            manager.createNotificationChannel(channel)
//        }
//
//        return NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Prank Engine Active")
//            .setContentText("Close app completely or stop service to remove crack overlay.")
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .build()
//    }
//
//    override fun onTaskRemoved(rootIntent: Intent?) {
//        // Stop service when app is swiped away from recent apps panel
//        stopSelf()
//        super.onTaskRemoved(rootIntent)
//    }
//
//    override fun onDestroy() {
//        cleanAllBackgroundTriggers()
//        removeOverlaySafely(activeCrackOverlay)
//        activeCrackOverlay = null
//        super.onDestroy()
//    }
//}



/**
 * 15-Year Developer Standard: High-fidelity WindowManager service overlay.
 * Handles system-wide global touch capturing, accelerometer shake, and timers in the background.
 * ✅ FIXED: Added FLAG_LAYOUT_NO_LIMITS and LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES (API >= 28)
 * to draw the glass fracture seamlessly edge-to-edge over the status bar and bottom navigation bar.
 * ✅ FIXED: Checks the SharedPreferences "IS_VIBRATIONENABLED" value under "app_settings"
 * to conditionally trigger vibration only when enabled by the user.
 */
class BrokenScreenService : Service(), SensorEventListener {

    private lateinit var windowManager: WindowManager
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    private var touchCatcherOverlay: View? = null
    private var activeCrackOverlay: View? = null

    // Background handlers to completely prevent memory leaks
    private val backgroundHandler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    private var selectedBgRes = R.drawable.broken_screen_2
    private var selectedCrackRes = R.drawable.broken_screen_1
    private var isShakeRegistered = false

    companion object {
        const val EXTRA_BACKGROUND = "EXTRA_BACKGROUND"
        const val EXTRA_CRACK = "EXTRA_CRACK"
        const val EXTRA_TOUCH_CATCHER = "EXTRA_TOUCH_CATCHER"
        const val EXTRA_PREVIEW_MODE = "EXTRA_PREVIEW_MODE"

        // Dynamic addition for Shake & Timer triggers from Fragment
        const val EXTRA_SHAKE_TRIGGER = "EXTRA_SHAKE_TRIGGER"
        const val EXTRA_TIMER_TRIGGER = "EXTRA_TIMER_TRIGGER"
        const val EXTRA_TIMER_DELAY = "EXTRA_TIMER_DELAY"

        private const val CHANNEL_ID = "broken_screen_prank_channel"
        private const val NOTIFICATION_ID = 4851
        private const val SHAKE_THRESHOLD = 14.0f // G-force threshold for reliable shake detection
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_NOT_STICKY

        selectedBgRes = intent.getIntExtra(EXTRA_BACKGROUND, R.drawable.broken_screen_2)
        selectedCrackRes = intent.getIntExtra(EXTRA_CRACK, R.drawable.broken_screen_1)
        val isTouchCatcher = intent.getBooleanExtra(EXTRA_TOUCH_CATCHER, false)
        val isPreview = intent.getBooleanExtra(EXTRA_PREVIEW_MODE, false)
        val isShakeTrigger = intent.getBooleanExtra(EXTRA_SHAKE_TRIGGER, false)
        val isTimerTrigger = intent.getBooleanExtra(EXTRA_TIMER_TRIGGER, false)

        // Clear any previous active triggers to prevent overlaps
        cleanAllBackgroundTriggers()

        when {
            isPreview -> {
                drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = true)
                // Auto close preview mode after 3.5 seconds
                backgroundHandler.postDelayed({
                    stopSelf()
                }, 3500)
            }
            isTouchCatcher -> {
                setupGlobalTouchCatcher(selectedBgRes, selectedCrackRes)
            }
            isShakeTrigger -> {
                setupBackgroundShakeDetector()
            }
            isTimerTrigger -> {
                val delayMillis = intent.getLongExtra(EXTRA_TIMER_DELAY, 10000L)
                setupBackgroundTimer(delayMillis)
            }
            else -> {
                // Instantly draw crack if no specific trigger state is passed
                triggerDeviceVibration()
                drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = false)
            }
        }

        return START_STICKY
    }

    /**
     * Creates an invisible full-screen touch interceptor window to catch global gestures.
     * Triggers crack on very first tap and unregisters itself immediately.
     */
    private fun setupGlobalTouchCatcher(bgRes: Int, crackRes: Int) {
        removeOverlaySafely(touchCatcherOverlay)

        val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        // ✅ FIXED: Added FLAG_LAYOUT_NO_LIMITS to ensure edge-to-edge touch captures
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            windowType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START

            // ✅ Allows overlay to draw behind displays with cutouts / punch hole cameras
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        val catcher = View(this)
        catcher.setOnTouchListener { _, _ ->
            // Trigger crack overlay break!
            triggerDeviceVibration()
            drawFullscreenCrack(bgRes, crackRes, isPreviewMode = false)

            // Instantly clean up the invisible touch layer so the victim can see the crack underneath
            cleanTouchCatcher()
            true
        }

        try {
            windowManager.addView(catcher, params)
            touchCatcherOverlay = catcher
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * SHAKE MODE: Registers hardware Accelerometer to listen for G-force in background.
     */
    private fun setupBackgroundShakeDetector() {
        if (isShakeRegistered) return
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        accelerometer?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
            isShakeRegistered = true
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gForce = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

        if (gForce > SHAKE_THRESHOLD) {
            // Unregister sensor first to avoid duplicate fires
            cleanSensorDetector()
            triggerDeviceVibration()
            drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = false)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    /**
     * TIMER MODE: Schedules background runnable to trigger crack after delay.
     */
    private fun setupBackgroundTimer(delayMillis: Long) {
        timerRunnable = Runnable {
            triggerDeviceVibration()
            drawFullscreenCrack(selectedBgRes, selectedCrackRes, isPreviewMode = false)
        }
        backgroundHandler.postDelayed(timerRunnable!!, delayMillis)
    }

    /**
     * Safely plays the broken screen audio effect from raw resources.
     * Prevents memory leaks by auto-releasing the MediaPlayer instance after playback.
     */
    private fun playCrackSound() {
        try {
            val mediaPlayer = MediaPlayer.create(this, R.raw.sound_broken)
            mediaPlayer?.setOnCompletionListener { mp ->
                mp.release() // Clean up player memory
            }
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Draws the realistic crack system overlay layer over the screen.
     * ✅ FIXED: Added FLAG_LAYOUT_NO_LIMITS to draw fullscreen and cut off top/bottom gaps.
     * ✅ FIXED: Applied LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES to bypass punch hole / camera notches.
     */
    private fun drawFullscreenCrack(bgRes: Int, crackRes: Int, isPreviewMode: Boolean = false) {
        if (activeCrackOverlay != null) return

        // Play the broken screen audio sound right before displaying overlay
        playCrackSound()

        val windowType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }

        // Window flag configurations
        val windowFlags = if (isPreviewMode) {
            // Preview mode allows normal touch checking options
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS // ✅ Edge-to-edge
        } else {
            // Real Prank Mode forces NOT_TOUCHABLE so it stays permanent
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS // ✅ Edge-to-edge
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            windowType,
            windowFlags,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START

            // ✅ Force full draw under the front camera punch hole / screen notches
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            }
        }

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_overlay_crack, null)

        val ivBg = view.findViewById<ImageView>(R.id.ivOverlayBg)
        val ivCrack = view.findViewById<ImageView>(R.id.ivOverlayCrack)

        ivBg.setImageResource(bgRes)
        ivCrack.setImageResource(crackRes)

        // Only preview mode allows close-on-tap
        if (isPreviewMode) {
            view.setOnClickListener {
                stopSelf()
            }
        }

        try {
            windowManager.addView(view, params)
            activeCrackOverlay = view
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Triggers haptic feedback / vibration to simulate heavy screen impact.
     * ✅ FIXED: Reads SharedPreferences dynamically and halts if the vibration setting is disabled.
     */
    private fun triggerDeviceVibration() {
        val prefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val isVibrationEnabled = prefs.getBoolean("IS_VIBRATIONENABLED", true)

        // ✅ If vibration is disabled by the user, return immediately and do NOT vibrate
        if (!isVibrationEnabled) return

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(450, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(450)
        }
    }

    private fun removeOverlaySafely(view: View?) {
        view?.let {
            try {
                windowManager.removeView(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun cleanTouchCatcher() {
        touchCatcherOverlay?.let {
            removeOverlaySafely(it)
            touchCatcherOverlay = null
        }
    }

    private fun cleanSensorDetector() {
        if (isShakeRegistered) {
            sensorManager?.unregisterListener(this)
            isShakeRegistered = false
        }
    }

    private fun cleanAllBackgroundTriggers() {
        cleanTouchCatcher()
        cleanSensorDetector()
        timerRunnable?.let {
            backgroundHandler.removeCallbacks(it)
            timerRunnable = null
        }
    }

    private fun createNotification(): Notification {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Prank Service Notification",
                NotificationManager.IMPORTANCE_LOW
            )
            manager.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Prank Engine Active")
            .setContentText("Close app completely or stop service to remove crack overlay.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        // Stop service when app is swiped away from recent apps panel
        stopSelf()
        super.onTaskRemoved(rootIntent)
    }

    override fun onDestroy() {
        cleanAllBackgroundTriggers()
        removeOverlaySafely(activeCrackOverlay)
        activeCrackOverlay = null
        super.onDestroy()
    }
}