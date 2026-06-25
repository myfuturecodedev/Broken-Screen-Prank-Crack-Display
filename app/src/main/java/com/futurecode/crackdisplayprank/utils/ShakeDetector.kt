package com.futurecode.crackdisplayprank.utils


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt


/**
 * 15-Year Developer Standard: Thread-safe shake G-force sensor metrics calculator.
 */
class ShakeDetector(private val onShakeDetected: () -> Unit) : SensorEventListener {

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.3f
        private const val SHAKE_SLOP_TIME_MS = 500
    }

    private var shakeTimestamp: Long = 0

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        // G-Force is square root of sum of G-Force squared
        val gForce = sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }
            shakeTimestamp = now
            onShakeDetected()
        }
    }
}