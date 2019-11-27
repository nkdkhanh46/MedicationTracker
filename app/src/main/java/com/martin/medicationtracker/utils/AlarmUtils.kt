package com.martin.medicationtracker.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.martin.medicationtracker.features.addmedication.AddMedicationViewModel
import java.util.*

class AlarmUtils {
    companion object {
        fun setReminder(context: Context, cls: Class<*>, hour: Int, min: Int) {
            val now = Calendar.getInstance()
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, min)
            cal.set(Calendar.SECOND, 0)

            if (cal.before(now)) cal.add(Calendar.DATE, 1)

            // Enable a receiver
            val receiver = ComponentName(context, cls)
            context.packageManager.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

            val intent = Intent(context, cls)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                AddMedicationViewModel.DAILY_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, cal.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent
            )
        }
    }
}