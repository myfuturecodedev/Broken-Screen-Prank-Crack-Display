package com.futurecode.crackdisplayprank.notification

import com.futurecode.crackdisplayprank.utils.getNotificationListFromPrefs


object NotificationRepository {
    val notifications = getNotificationListFromPrefs()
}