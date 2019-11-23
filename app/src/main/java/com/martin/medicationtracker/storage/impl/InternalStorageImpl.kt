package com.martin.medicationtracker.storage.impl

import android.content.Context
import com.martin.medicationtracker.storage.InternalStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageImpl @Inject constructor(private val context: Context): InternalStorage {
}