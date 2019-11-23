package com.martin.medicationtracker.application

import android.app.Application
import com.martin.medicationtracker.injection.AppComponent
import com.martin.medicationtracker.injection.DaggerAppComponent
import com.martin.medicationtracker.injection.modules.AppModule

class MainApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    @Suppress("DEPRECATION")
    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}