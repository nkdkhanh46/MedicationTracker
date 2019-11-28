package com.martin.medicationtracker.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.martin.medicationtracker.features.home.HomeActivity
import com.martin.medicationtracker.utils.AlarmUtils
import com.martin.medicationtracker.utils.NotificationUtils

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) return

        //Trigger the notification
        NotificationUtils.showNotification(context, HomeActivity::class.java, "Reminder to take medicine", "Take your medicine on time")
    }
}