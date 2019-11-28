package com.martin.medicationtracker.features.addsymptoms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.medicationtracker.models.SymptomSeverity
import com.martin.medicationtracker.models.Symptoms
import com.martin.medicationtracker.repositories.DataRepository
import com.martin.medicationtracker.utils.DateTimeUtils
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddSymptomsViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {

    val date = MutableLiveData<String>()
    val addOthers = MutableLiveData<Boolean>()
    val saveSuccess = MutableLiveData<Boolean>()

    init {
        date.value = DateTimeUtils.calToFullDateString(Calendar.getInstance())
        addOthers.value = false
    }

    fun saveSymptoms(
        otherSymptoms: String,
        couchSeverity: SymptomSeverity,
        wheezeSeverity: SymptomSeverity
    ) {
        val others = if (addOthers.value == true) otherSymptoms else ""
        val symptoms = Symptoms(couchSeverity.value, wheezeSeverity.value, Calendar.getInstance().timeInMillis, others)
        viewModelScope.launch {
            dataRepository.addSymptoms(symptoms)
            saveSuccess.value = true
        }
    }

    fun toggleAddMore() {
        addOthers.value = !(addOthers.value?: false)
    }
}