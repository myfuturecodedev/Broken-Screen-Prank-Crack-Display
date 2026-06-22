package com.futurecode.crackdisplayprank.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PrankReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val serviceIntent =
            Intent(context, BrokenScreenService::class.java)

        context.startService(serviceIntent)
    }
}