package com.martin.medicationtracker.repositories.impl

import androidx.lifecycle.LiveData
import com.martin.medicationtracker.database.AppDatabase
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.repositories.MedicationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicationRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : MedicationRepository {

    override fun getAllMedications(): LiveData<List<Medication>> = appDatabase.medicationDao().getAllMedications()

    override suspend fun addMedication(medication: Medication): Long {
        return appDatabase.medicationDao().insert(medication)
    }
}