package com.martin.medicationtracker.models

import com.martin.medicationtracker.R

enum class SymptomSeverity(val value: Int, val displayValue: Int) {
    MILD(1, R.string.symptom_severity_mild),
    MODERATE(2, R.string.symptom_severity_moderate),
    SEVERE(3, R.string.symptom_severity_severe);

    companion object {
        fun findSeverity(value: Int): SymptomSeverity {
            for (severity in values()) {
                if (severity.value == value) return severity
            }

            return MILD
        }
    }
}