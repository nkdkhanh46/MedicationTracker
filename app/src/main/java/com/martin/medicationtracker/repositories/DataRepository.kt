package com.martin.medicationtracker.repositories

import androidx.lifecycle.LiveData
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.models.MedicationRecord
import com.martin.medicationtracker.models.MedicationStatus
import com.martin.medicationtracker.models.Symptoms

interface DataRepository {

    fun getAllMedications(): LiveData<List<Medication>>
    fun getMedicationsWithStatus(date: String): LiveData<List<MedicationStatus>>
    fun getAllMedicationRecords(): LiveData<List<MedicationRecord>>

    suspend fun addMedication(medication: Medication): Long
    suspend fun addMedicationRecord(record: MedicationRecord): Long
    suspend fun addSymptoms(symptoms: Symptoms): Long
}