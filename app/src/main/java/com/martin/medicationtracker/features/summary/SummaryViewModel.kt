package com.martin.medicationtracker.features.summary

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.medicationtracker.models.*
import com.martin.medicationtracker.repositories.DataRepository
import com.martin.medicationtracker.repositories.UserRepository
import com.martin.medicationtracker.utils.DateTimeUtils
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class SummaryViewModel @Inject constructor(
    private val context: Context,
    private val dataRepository: DataRepository
): ViewModel() {
    val records: LiveData<List<MedicationRecord>> = dataRepository.getAllMedicationRecords()
    val symptoms: LiveData<List<Symptoms>> = dataRepository.getAllSymptoms()
    val summaryList: MediatorLiveData<List<Summary>> = MediatorLiveData()

    init {
        summaryList.addSource(records) {
            updateSummaryList()
        }
    }

    private fun updateSummaryList() {
        val result = ArrayList<Summary>()
        val recordSummary = records.value?.map { record -> Summary(context, record) } ?: ArrayList()
        val symptomsSummary = symptoms.value?.map { symptoms -> Summary(context, symptoms) } ?: ArrayList()
        result.addAll(recordSummary)
        result.addAll(symptomsSummary)
        result.sortBy { summary -> summary.createDateInMils }
        summaryList.value = result
    }
}