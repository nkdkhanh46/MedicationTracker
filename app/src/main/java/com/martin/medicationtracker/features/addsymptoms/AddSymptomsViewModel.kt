package com.martin.medicationtracker.features.addsymptoms

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.receivers.AlarmReceiver
import com.martin.medicationtracker.repositories.MedicationRepository
import com.martin.medicationtracker.utils.AlarmUtils
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class AddSymptomsViewModel @Inject constructor(
    private val context: Context,
    private val medicationRepository: MedicationRepository
): ViewModel() {

    companion object {
        const val NOTIFICATION_NAME_EMPTY = 1
        const val NOTIFICATION_VISIT_DATE_EMPTY = 2
        const val NOTIFICATION_TIMES_EMPTY = 3
        const val NOTIFICATION_ADD_MEDICATION_SUCCESS = 4

        const val DAILY_REMINDER_REQUEST_CODE = 1000
    }

    val editMode = MutableLiveData<Boolean>()
    val visitDate = MutableLiveData<String>()
    val frequency = MutableLiveData<String>()
    val dose = MutableLiveData<String>()
    val beforeMeal = MutableLiveData<Boolean>()
    val times = MutableLiveData<ArrayList<String>>()
    val notification = MutableLiveData<Int>()
    val medications: LiveData<List<Medication>> = medicationRepository.getAllMedications()

    private var medication: Medication? = null

    init {
        editMode.value = false
    }

    private fun getNewMedication(): Medication {
        return Medication(
            "",
            "",
            "Tablet",
            1,
            1,
            true,
            "",
            Calendar.getInstance().timeInMillis
        )
    }

    private fun bindMedication() {
        medication?.let { medication ->
            frequency.value = medication.frequency.toString()
            dose.value = medication.dose.toString()
            beforeMeal.value = medication.beforeMeal
        }
    }

    fun increaseFrequency() {
        medication?.let { medication ->
            val value = medication.frequency+1
            medication.frequency = value
            frequency.value = value.toString()
        }
    }

    fun decreaseFrequency() {
        medication?.let { medication ->
            val value = if (medication.frequency <= 1) 1 else medication.frequency-1
            medication.frequency = value
            frequency.value = value.toString()
        }
    }

    fun increaseDose() {
        medication?.let { medication ->
            val value = medication.dose+1
            medication.dose = value
            dose.value = value.toString()
        }
    }

    fun decreaseDose() {
        medication?.let { medication ->
            val value = if (medication.dose <= 1) 1 else medication.dose-1
            medication.dose = value
            dose.value = value.toString()
        }
    }

    fun updateMedicationName(name: String) {
        medication?.let { medication ->
            medication.name = name
        }
    }

    fun updateDoctorVisitDate(year: Int, month: Int, date: Int) {
        val visitDate = "$date/$month/$year"
        this.visitDate.value = visitDate
    }

    fun updateBeforeMeal(isBefore: Boolean) {
        medication?.let { medication ->
            medication.beforeMeal = isBefore
            beforeMeal.value = isBefore
        }
    }

    fun addTime(hour: Int, minute: Int) {
        medication?.let { medication ->
            val time = String.format("%02d:%02d", hour, minute)
            val newTimes = times.value?: ArrayList()
            newTimes.add(time)
            newTimes.sort()
            times.value = newTimes
            medication.times = newTimes.joinToString(";")
        }
    }

    fun saveMedication() {
        if (medication == null) return

        if (!checkFieldsValidity()) return

        viewModelScope.launch {
            medication?.updatedDate = Calendar.getInstance().timeInMillis
            medicationRepository.addMedication(medication!!)
            setReminders(medication!!.times)
            notification.value = NOTIFICATION_ADD_MEDICATION_SUCCESS
            editMode.value = false
        }
    }

    private fun setReminders(times: String) {
        val array = times.split(";")
        array.forEach { time ->
            val values = time.split(":")
            AlarmUtils.setReminder(context, AlarmReceiver::class.java, values[0].toInt(), values[1].toInt())
        }
    }

    private fun checkFieldsValidity(): Boolean {
        if (medication == null) return false

        if (medication!!.name.isEmpty()) {
            notification.value = NOTIFICATION_NAME_EMPTY
            return false
        }

        medication?.doctorVisitDate = visitDate.value ?: ""
        if (medication!!.doctorVisitDate.isEmpty()) {
            notification.value = NOTIFICATION_VISIT_DATE_EMPTY
            return false
        }

        if (medication!!.times.isEmpty()) {
            notification.value = NOTIFICATION_TIMES_EMPTY
            return false
        }

        return true
    }

    fun createNewMedication() {
        editMode.value = true
        times.value?.clear()
        medication = getNewMedication()
        bindMedication()
    }
}