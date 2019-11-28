package com.martin.medicationtracker.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {

        private const val FORMAT_DATE = "dd/MM/yyyy"
        private const val FORMAT_TIME = "hh:mm"
        private const val FORMAT_FULL_DATE = "dd MMMM yyyy"

        fun calToDateString(cal: Calendar): String {
            val sdf = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return sdf.format(cal.time)
        }

        fun timeMilToDateString(timeInMils: Long): String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = timeInMils
            val sdf = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return sdf.format(cal.time)
        }

        fun timeMilToTimeString(timeInMils: Long): String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = timeInMils
            val sdf = SimpleDateFormat(FORMAT_TIME, Locale.getDefault())
            return sdf.format(cal.time)
        }

        fun calToFullDateString(cal: Calendar): String {
            val sdf = SimpleDateFormat(FORMAT_FULL_DATE, Locale.getDefault())
            return sdf.format(cal.time)
        }
    }
}