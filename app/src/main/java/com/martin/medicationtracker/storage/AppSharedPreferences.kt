package com.martin.medicationtracker.storage

interface AppSharedPreferences {
    fun setTutorialCompleted(completed: Boolean)
    fun getTutorialCompleted(): Boolean
}