package com.martin.medicationtracker.repositories.impl

import androidx.lifecycle.LiveData
import com.martin.medicationtracker.database.AppDatabase
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.models.MedicationRecord
import com.martin.medicationtracker.models.MedicationStatus
import com.martin.medicationtracker.models.Symptoms
import com.martin.medicationtracker.repositories.DataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : DataRepository {

    override fun getAllMedications(): LiveData<List<Medication>> = appDatabase.medicationDao().getAllMedications()

    override fun getMedicationsWithStatus(date: String): LiveData<List<MedicationStatus>> = appDatabase.medicationDao().getMedicationsWithStatus(date)

    override fun getAllMedicationRecords(): LiveData<List<MedicationRecord>> = appDatabase.medicationDao().getAllMedicationRecords()

    override fun getAllSymptoms(): LiveData<List<Symptoms>> = appDatabase.symptomsDao().getAllSymptoms()

    override suspend fun addMedication(medication: Medication): Long {
        return appDatabase.medicationDao().insert(medication)
    }

    override suspend fun addMedicationRecord(record: MedicationRecord): Long {
        return appDatabase.medicationDao().insert(record)
    }

    override suspend fun addSymptoms(symptoms: Symptoms): Long {
        return appDatabase.symptomsDao().insert(symptoms)
    }
}