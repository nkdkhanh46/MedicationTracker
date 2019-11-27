package com.martin.medicationtracker.models

class MedicationStatus(
    doctorVisitDate: String,
    name: String,
    form: String,
    frequency: Int,
    dose: Int,
    beforeMeal: Boolean,
    times: String,
    updatedDate: Long
): Medication(doctorVisitDate, name, form, frequency, dose, beforeMeal, times, updatedDate) {
    var taken: Int = 0
}