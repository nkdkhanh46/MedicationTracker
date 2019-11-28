package com.martin.medicationtracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_record")
class MedicationRecord(
    @ColumnInfo(name = "created_date")
    var createdDate: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "form")
    var form: String,
    @ColumnInfo(name = "dose")
    var dose: Int,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "medication_id")
    var medicationId: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}