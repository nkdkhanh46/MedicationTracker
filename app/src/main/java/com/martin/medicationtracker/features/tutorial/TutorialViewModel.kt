package com.martin.medicationtracker.features.tutorial

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martin.medicationtracker.R
import com.martin.medicationtracker.models.Tutorial
import com.martin.medicationtracker.storage.AppSharedPreferences
import javax.inject.Inject

class TutorialViewModel @Inject constructor(
    private val context: Context,
    private val sharedPreferences: AppSharedPreferences): ViewModel() {

    val title = ObservableField("")
    val description = ObservableField("")
    val isLastPage = ObservableField(false)

    val tutorialCompleted = MutableLiveData<Boolean>()

    val tutorials: List<Tutorial>

    init {
        tutorialCompleted.value = sharedPreferences.getTutorialCompleted()
        tutorials = getData()
        changeCurrentPage(0)
    }

    fun changeCurrentPage(position: Int) {
        val tutorial: Tutorial = tutorials[position]
        title.set(getString(tutorial.title))
        description.set(getString(tutorial.description))
        isLastPage.set(position == tutorials.size-1)
    }

    private fun getString(res: Int): String {
        return context.getString(res)
    }

    private fun getData(): List<Tutorial> {
        val tutorials = ArrayList<Tutorial>()
        tutorials.add(Tutorial(
            R.string.tutotial_title_1,
            R.string.tutotial_description_1,
            R.drawable.img_tutorial_1)
        )
        tutorials.add(Tutorial(R.string.tutotial_title_2,
            R.string.tutotial_description_2,
            R.drawable.img_tutorial_2)
        )
        tutorials.add(Tutorial(R.string.tutotial_title_3,
            R.string.tutotial_description_3,
            R.drawable.img_tutorial_3)
        )
        return tutorials
    }

    fun completeTutorial() {
        sharedPreferences.setTutorialCompleted(true)
    }
}