package com.martin.medicationtracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.martin.medicationtracker.models.Symptoms

@Dao
interface SymptomsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(symptoms: Symptoms): Long

    @Query("SELECT * FROM symptoms ORDER BY id DESC" )
    fun getAllSymptoms() : LiveData<List<Symptoms>>
}