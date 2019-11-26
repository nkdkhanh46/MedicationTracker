package com.martin.medicationtracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.martin.medicationtracker.database.AppDatabase.Companion.DB_VERSION
import com.martin.medicationtracker.models.Medication

@Database(entities = [Medication::class], version = DB_VERSION)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        const val DATABASE_NAME = "MedicationTrackerDb"
    }

    abstract fun medicationDao(): MedicationDao
}