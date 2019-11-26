package com.martin.medicationtracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martin.medicationtracker.models.Medication

@Dao
interface MedicationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medication: Medication): Long

    @Query("SELECT * FROM medication ORDER BY updated_date ASC" )
    fun getAllMedications() : LiveData<List<Medication>>
}