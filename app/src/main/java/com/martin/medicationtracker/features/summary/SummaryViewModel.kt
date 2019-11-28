package com.martin.medicationtracker.features.summary

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.martin.medicationtracker.models.*
import com.martin.medicationtracker.repositories.DataRepository
import javax.inject.Inject
import kotlin.collections.ArrayList

class SummaryViewModel @Inject constructor(
    private val context: Context,
    dataRepository: DataRepository
): ViewModel() {
    private val records: LiveData<List<MedicationRecord>> = dataRepository.getAllMedicationRecords()
    private val symptoms: LiveData<List<Symptoms>> = dataRepository.getAllSymptoms()
    val summaries: MediatorLiveData<List<Summary>> = MediatorLiveData()

    init {
        summaries.addSource(records) {
            updateSummaryList()
        }
        summaries.addSource(symptoms) {
            updateSummaryList()
        }
    }

    private fun updateSummaryList() {
        val summaries = ArrayList<Summary>()
        val recordSummary = records.value?.map { record -> Summary(context, record) } ?: ArrayList()
        val symptomsSummary = symptoms.value?.map { symptoms -> Summary(context, symptoms) } ?: ArrayList()
        summaries.addAll(recordSummary)
        summaries.addAll(symptomsSummary)
        summaries.sortBy { summary -> summary.createDateInMils }
        val groupedSummaries = ArrayList<Summary>()
        var currentHeader = ""
        for (summary in summaries) {
            val summaryDate = summary.date
            if (currentHeader.isEmpty() || currentHeader != summaryDate) {
                groupedSummaries.add(Summary(true, summaryDate))
                currentHeader = summaryDate
            }
            groupedSummaries.add(summary)
        }
        this.summaries.value = groupedSummaries
    }
}