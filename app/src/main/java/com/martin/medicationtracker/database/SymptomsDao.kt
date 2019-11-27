package com.martin.medicationtracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.models.MedicationRecord
import com.martin.medicationtracker.models.MedicationStatus

@Dao
interface MedicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medication: Medication): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: MedicationRecord): Long

    @Query("SELECT medication.*, CASE WHEN EXISTS(select * from medication_record where medication_id = medication.id and created_date = :date) then 1 else 0 end as taken FROM medication ORDER BY updated_date ASC")
//    @Query("SELECT medication.*, CASE WHEN EXISTS(select * from medication_record where medication_id = medication.id) then 1 else 0 end as taken FROM medication ORDER BY updated_date ASC")
    fun getMedicationsWithStatus(date: String) : LiveData<List<MedicationStatus>>

    @Query("SELECT * FROM medication ORDER BY updated_date ASC" )
    fun getAllMedications() : LiveData<List<Medication>>

    @Query("SELECT * FROM medication_record" )
    fun getAllMedicationRecords() : LiveData<List<MedicationRecord>>
}