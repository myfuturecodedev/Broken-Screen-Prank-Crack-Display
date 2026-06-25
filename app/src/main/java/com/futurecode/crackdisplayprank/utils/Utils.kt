package com.futurecode.crackdisplayprank.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.activity.MyApplication
import com.futurecode.crackdisplayprank.ads.AdInterface
import com.futurecode.crackdisplayprank.ads.interstitial_ad.FullScreenAdsHelper
import com.futurecode.crackdisplayprank.model.Promo

import com.google.gson.Gson

import org.json.JSONArray
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Random

object Utils {
    fun Float.dpToPx(activity: Activity): Float =
        this * activity.resources.displayMetrics.density

    fun NumberPicker.removeDividers() {
        try {
            val fields: Array<Field> = NumberPicker::class.java.declaredFields
            for (field in fields) {
                if (field.name == "mSelectionDivider") {
                    field.isAccessible = true
                    field.set(this, null)
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SoonBlockedPrivateApi")
    fun NumberPicker.applySelectedColor(hexColor: String) {
        // Color selected number green, others gray
        try {
            val selectorWheelPaintField =
                NumberPicker::class.java.getDeclaredField("mSelectorWheelPaint")
            selectorWheelPaintField.isAccessible = true
            (selectorWheelPaintField.get(this) as Paint).apply {
                color = Color.parseColor(hexColor)
                textSize = 52f * resources.displayMetrics.scaledDensity
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            invalidate()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Use a custom formatter to style non-selected items gray
        this.setFormatter { value -> value.toString() }
    }

    fun getWidth(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowmanager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowmanager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }

    fun formatTimestampToHours(timestamp: Long): String {
        val diffMinutes = (System.currentTimeMillis() - timestamp) / (1000 * 60)

        return when {
            diffMinutes < 60 -> "just now"
            else -> {
                val hours = diffMinutes / 60
                if (hours == 1L) "1 hr" else "$hours hr"
            }
        }
    }

    fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) return true
        }
        return false
    }

    fun showDatePickerDialog(
        context: Context,
        yearr: Int,
        initialYear: Int?,
        initialMonth: Int?,
        initialDay: Int?,
        selectedDate: (String) -> Unit
    ) {
        val calendar = Calendar.getInstance()

        // If initial date values are provided, set the calendar to that date.
        if (initialYear != null && initialMonth != null && initialDay != null) {
            // Subtract 1 from initialMonth because months are 0-based in DatePickerDialog and Calendar
            calendar.set(initialYear, initialMonth - 1, initialDay)
        } else {
            // Otherwise, allow selection up to `yearr` years in the past
            calendar.add(Calendar.YEAR, -yearr)
        }

        val selectedYear = calendar.get(Calendar.YEAR)
        val selectedMonth = calendar.get(Calendar.MONTH)
        val selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Set the maxDate to allow the user to select dates from `yearr` years ago
        val maxCalendar = Calendar.getInstance()
        maxCalendar.add(Calendar.YEAR, -yearr)
        val maxDate = maxCalendar.timeInMillis

        // Create the DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            context,
            { _, pickedYear, pickedMonth, pickedDayOfMonth ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(pickedYear, pickedMonth, pickedDayOfMonth)
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedCalendar.time)

                selectedDate(formattedDate) // Callback function with the selected date
            },
            selectedYear,
            selectedMonth,
            selectedDayOfMonth
        )

        // Set the max date to limit the selection to `yearr` years ago
        datePickerDialog.datePicker.maxDate = maxDate

        // If a date was previously selected, set that date on the dialog
        if (initialYear != null && initialMonth != null && initialDay != null) {
            // Adjust the month by subtracting 1 as DatePickerDialog expects 0-based month
            datePickerDialog.updateDate(initialYear, initialMonth - 1, initialDay)
        }

        // Show the date picker dialog
        datePickerDialog.show()
    }



    fun openCustomChrome(activity: Activity, url: String) {
        try {
            val customIntent = CustomTabsIntent.Builder()

            customIntent.setToolbarColor(ContextCompat.getColor(activity, R.color.black))

            // we are calling below method after
            // setting our toolbar color.
            val customTabsIntent = customIntent.build()
            val packageName = "com.android.chrome"
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(activity, Uri.parse(url))
        } catch (e: Exception) {
            openBrowser(activity, url)
            e.printStackTrace()
        }

    }

    fun openBrowser(activity: Activity, url: String) {
        val link = url.ifBlank {
            "https://www.google.com"
        }

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setPackage("com.android.chrome")
        }

        try {
            activity.startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            // Chrome not available, try default browser
            val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                setPackage(null)
            }

            // ✅ Only try if there's an app to handle it
            if (fallbackIntent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(fallbackIntent)
            } else {
                Toast.makeText(activity, "No browser found to open the link", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun jsonToStringList(json: String?): List<String> {
        if (json.isNullOrEmpty()) return emptyList()
        val list = mutableListOf<String>()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            list.add(array.getString(i))
        }
        return list
    }

    fun getRandomUrls(activity: Context): String {
        val list = jsonToStringList(MyApplication.app.prefManager.customUrls)
        return if (list.isNotEmpty()) {
            val random = Random()
            val randomString = list[random.nextInt(list.size)]

            randomString
        } else {
            "https://www.google.com/"
        }

    }


    fun View.setAdClickListener(
        activity: Activity,
        adsHelper: FullScreenAdsHelper, // Pass the helper here
        isShowEveryTime: Boolean = false,
        onFinished: () -> Unit
    ) {
        setOnClickListener {
            ProgressBarUtils.showProgressDialog(activity)
            // Use the passed helper instead of creating a new one
            adsHelper.showInterstitialAds(isShowEveryTime, object : AdInterface {
                override fun finished() {
                    ProgressBarUtils.hideProgressDialog()
                    onFinished()
                }
            })
        }
    }


    fun updateBaseContextLocale(context: Context): Context {
        val languageCode = MyApplication.app.prefManager.selectedLanguage ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        config.fontScale = 1.0f

        return context.createConfigurationContext(config)
    }


    fun getPromosListFromPrefs(): List<Promo> {
        return try {
            val jsonString = MyApplication.app.prefManager.promosList

            if (jsonString.isNullOrEmpty()) return emptyList()

            Gson().fromJson(
                jsonString,
                Array<Promo>::class.java
            )?.toList() ?: emptyList()

        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    /**
     * Robust extension function to configure the Status Bar background color
     * and manage system icon contrast levels across all Android versions.
     *
     * @param colorResId The layout resource color ID (e.g., R.color.primary_blue)
     * @param isLightStatusIcon True for White icons (dark backgrounds), False for Dark icons (light backgrounds)
     */
    fun Activity.setStatusBarColorWithVersionHandling(@ColorRes colorResId: Int, isLightStatusIcon: Boolean) {
        val color = ContextCompat.getColor(this, colorResId)
        val window = this.window

        // 1. Fully clear legacy translucent flags that block solid colors on older APIs
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // 2. Set the background color
        window.statusBarColor = color

        // 3. Android 15+ (API 35/36) Edge-to-Edge background rendering enforcement override
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            // Keeps the system bar background drawing behavior consistent with older versions
            window.isStatusBarContrastEnforced = false
        }

        // 4. Safely manage text/icon contrast across all targets without raw bitmask flags
        val decorView = window.decorView
        WindowInsetsControllerCompat(window, decorView).apply {
            // true = dark text/icons (for light backgrounds)
            // false = light text/icons (for dark backgrounds)
            isAppearanceLightStatusBars = !isLightStatusIcon
        }
    }

}


