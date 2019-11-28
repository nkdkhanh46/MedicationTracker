package com.martin.medicationtracker.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Symptoms(
    @ColumnInfo(name = "couch_level")
    var couchLevel: Int,
    @ColumnInfo(name = "wheeze_level")
    var wheezeLevel: Int,
    @ColumnInfo(name = "created_date")
    var createdDate: Long,
    @ColumnInfo(name = "others")
    var others: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}