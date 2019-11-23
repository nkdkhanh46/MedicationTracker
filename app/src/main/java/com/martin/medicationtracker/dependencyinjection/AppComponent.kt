package com.martin.medicationtracker.dependencyinjection

import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.dependencyinjection.modules.AppModule
import com.martin.medicationtracker.dependencyinjection.modules.ViewModelModule
import com.martin.medicationtracker.features.home.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(target: BaseActivity)
    fun inject(target: HomeActivity)
}