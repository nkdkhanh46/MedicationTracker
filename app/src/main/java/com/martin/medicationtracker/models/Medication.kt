package com.martin.medicationtracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
open class Medication(
    @ColumnInfo(name = "doctor_visit_date")
    var doctorVisitDate: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "form")
    var form: String,
    @ColumnInfo(name = "frequency")
    var frequency: Int,
    @ColumnInfo(name = "dose")
    var dose: Int,
    @ColumnInfo(name = "use_before_meal")
    var beforeMeal: Boolean,
    @ColumnInfo(name = "use_times")
    var times: String,
    @ColumnInfo(name = "updated_date")
    var updatedDate: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    fun getUsingTimes() = times.split(";")
}