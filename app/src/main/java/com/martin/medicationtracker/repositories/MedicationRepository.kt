package com.martin.medicationtracker.repositories

import androidx.lifecycle.LiveData
import com.martin.medicationtracker.models.Medication

interface MedicationRepository {

    fun getAllMedications(): LiveData<List<Medication>>

    suspend fun addMedication(medication: Medication): Long
}