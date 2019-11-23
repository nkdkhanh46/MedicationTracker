package com.martin.medicationtracker.storage.impl

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.martin.medicationtracker.storage.AppSharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesImpl @Inject constructor(private val context: Context):
    AppSharedPreferences {

    companion object {
        const val KEY_TUTORIAL_COMPLETED = "TutorialCompleted"
    }

    private fun getSharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private fun getEditor(): SharedPreferences.Editor = getSharedPreferences().edit()

    private fun setString(key: String, value: String?) {
        val editor = getEditor()
        editor.putString(key, value)
        editor.apply()
    }

    private fun getString(key: String): String {
        return getSharedPreferences().getString(key, "") ?: ""
    }

    private fun setBoolean(key: String, value: Boolean) {
        val editor = getEditor()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String): Boolean {
        return getSharedPreferences().getBoolean(key, false)
    }

    override fun setTutorialCompleted(completed: Boolean) {
        setBoolean(KEY_TUTORIAL_COMPLETED, completed)
    }

    override fun getTutorialCompleted(): Boolean {
        return getBoolean(KEY_TUTORIAL_COMPLETED)
    }
}