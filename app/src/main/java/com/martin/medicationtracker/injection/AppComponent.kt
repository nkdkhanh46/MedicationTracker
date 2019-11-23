package com.martin.medicationtracker.injection

import com.martin.medicationtracker.features.tutorial.TutorialActivity
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.injection.modules.AppModule
import com.martin.medicationtracker.injection.modules.ViewModelModule
import com.martin.medicationtracker.features.home.HomeActivity
import com.martin.medicationtracker.injection.modules.StorageModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class, StorageModule::class])
@Singleton
interface AppComponent {
    fun inject(target: BaseActivity)
    fun inject(target: HomeActivity)
    fun inject(target: TutorialActivity)
}