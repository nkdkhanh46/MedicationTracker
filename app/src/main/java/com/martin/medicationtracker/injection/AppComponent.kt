package com.martin.medicationtracker.injection

import com.martin.medicationtracker.features.tutorial.TutorialActivity
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.features.addmedication.AddMedicationActivity
import com.martin.medicationtracker.features.home.HomeActivity
import com.martin.medicationtracker.injection.modules.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class, ViewModelModule::class, StorageModule::class,
    RepositoryModule::class, DatabaseModule::class
])

@Singleton
interface AppComponent {
    fun inject(target: BaseActivity)
    fun inject(target: HomeActivity)
    fun inject(target: TutorialActivity)
    fun inject(target: AddMedicationActivity)
}