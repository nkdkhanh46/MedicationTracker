package com.martin.medicationtracker.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.models.MedicationRecord
import com.martin.medicationtracker.models.MedicationStatus
import com.martin.medicationtracker.models.User
import com.martin.medicationtracker.repositories.MedicationRepository
import com.martin.medicationtracker.repositories.UserRepository
import com.martin.medicationtracker.utils.DateTimeUtils
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    private val medicationRepository: MedicationRepository
): ViewModel() {

    val user: LiveData<User> = userRepository.getCurrentUser()
    val medications: LiveData<List<MedicationStatus>> = medicationRepository.getMedicationsWithStatus(
        DateTimeUtils.calToDateString(Calendar.getInstance())
    )
    val medicationRecords: LiveData<List<MedicationRecord>> = medicationRepository.getAllMedicationRecords()

    val username = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val bloodType = MutableLiveData<String>()
    val avatar = MutableLiveData<String>()
    val today = MutableLiveData<String>()

    fun bindUserInformation(user: User) {
        username.value = user.name
        age.value = "${user.age}"
        bloodType.value = user.bloodType
        avatar.value = user.avatar
    }

    fun confirmMedicationTaken(medication: Medication) {
        createMedicationRecords(medication)
    }

    private fun createMedicationRecords(medication: Medication) {
        medication.getUsingTimes().forEach { time ->
            val record = MedicationRecord(
                DateTimeUtils.calToDateString(Calendar.getInstance()),
                medication.form,
                medication.dose,
                time,
                medication.id
            )

            viewModelScope.launch {
                medicationRepository.addMedicationRecord(record)
            }
        }
    }

    init {
        today.value = DateTimeUtils.calToDateString(Calendar.getInstance())
    }
}