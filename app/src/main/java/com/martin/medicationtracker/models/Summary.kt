package com.martin.medicationtracker.models

import android.content.Context
import com.martin.medicationtracker.R
import com.martin.medicationtracker.utils.DateTimeUtils

class Summary {

    var isHeader = false
    var date: String = ""
    var time: String = ""
    var value: String = ""
    val createDateInMils: Long
        get() = DateTimeUtils.stringToTimeInMils("$date $time")

    constructor(isHeader: Boolean, date: String) {
        this.isHeader = isHeader
        this.date = date
    }

    constructor(context: Context, record: MedicationRecord) {
        date = record.createdDate
        time = record.time
        value = String.format(context.getString(R.string.summary_record_value), record.dose, record.form, record.name)
    }

    constructor(context: Context, symptoms: Symptoms) {
        date = DateTimeUtils.timeMilToDateString(symptoms.createdDate)
        time = DateTimeUtils.timeMilToTimeString(symptoms.createdDate)
        val couch = SymptomSeverity.findSeverity(symptoms.couchLevel)
        val wheeze = SymptomSeverity.findSeverity(symptoms.wheezeLevel)
        value = String.format(context.getString(R.string.summary_symptoms_value), context.getString(couch.displayValue), context.getString(wheeze.displayValue), symptoms.others)
    }
}