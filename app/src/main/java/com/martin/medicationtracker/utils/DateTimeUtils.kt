package com.martin.medicationtracker.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {

        private const val FORMAT_DATE = "dd/MM/yyyy"

        fun calToDateString(cal: Calendar): String {
            val sdf = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
            return sdf.format(cal.time)
        }
    }
}