package com.martin.medicationtracker.repositories

import androidx.lifecycle.LiveData
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.models.MedicationRecord
import com.martin.medicationtracker.models.MedicationStatus

interface MedicationRepository {

    fun getAllMedications(): LiveData<List<Medication>>
    fun getMedicationsWithStatus(date: String): LiveData<List<MedicationStatus>>
    fun getAllMedicationRecords(): LiveData<List<MedicationRecord>>

    suspend fun addMedication(medication: Medication): Long
    suspend fun addMedicationRecord(record: MedicationRecord): Long
}