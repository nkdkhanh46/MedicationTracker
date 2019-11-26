package com.martin.medicationtracker.repositories

import androidx.lifecycle.LiveData
import com.martin.medicationtracker.models.User

interface UserRepository {
    fun getCurrentUser(): LiveData<User>
}