package com.martin.medicationtracker.injection.modules

import com.martin.medicationtracker.repositories.MedicationRepository
import com.martin.medicationtracker.repositories.UserRepository
import com.martin.medicationtracker.repositories.impl.MedicationRepositoryImpl
import com.martin.medicationtracker.repositories.impl.UserRepositoryImpl
import com.martin.medicationtracker.storage.AppSharedPreferences
import com.martin.medicationtracker.storage.InternalStorage
import com.martin.medicationtracker.storage.impl.InternalStorageImpl
import com.martin.medicationtracker.storage.impl.SharedPreferencesImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl

    @Provides
    fun provideMedicationRepository(impl: MedicationRepositoryImpl): MedicationRepository = impl
}