package com.martin.medicationtracker.application

import android.app.Application
import com.martin.medicationtracker.dependencyinjection.AppComponent
import com.martin.medicationtracker.dependencyinjection.DaggerAppComponent
import com.martin.medicationtracker.dependencyinjection.modules.AppModule

class MainApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    @Suppress("DEPRECATION")
    private fun initDagger() {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}