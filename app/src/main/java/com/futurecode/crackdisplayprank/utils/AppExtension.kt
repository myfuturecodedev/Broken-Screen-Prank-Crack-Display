package com.futurecode.crackdisplayprank.utils

import android.media.MediaPlayer
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.futurecode.crackdisplayprank.R
import com.futurecode.crackdisplayprank.notification.NotificationModel
import com.futurecode.crackdisplayprank.activity.MyApplication
import com.google.gson.Gson

fun getNotificationListFromPrefs(): List<NotificationModel> {
    return try {
        val jsonString = MyApplication.app.prefManager.notificationList

        if (jsonString.isNullOrEmpty()) return emptyList()

        Gson().fromJson(
            jsonString,
            Array<NotificationModel>::class.java
        )?.toList() ?: emptyList()

    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
