package com.martin.medicationtracker.injection.modules

import com.martin.medicationtracker.storage.AppSharedPreferences
import com.martin.medicationtracker.storage.InternalStorage
import com.martin.medicationtracker.storage.impl.InternalStorageImpl
import com.martin.medicationtracker.storage.impl.SharedPreferencesImpl
import dagger.Module
import dagger.Provides

@Module
class StorageModule {
    @Provides
    fun provideAppSharedPreferences(impl: SharedPreferencesImpl): AppSharedPreferences = impl

    @Provides
    fun provideInternalStorage(impl: InternalStorageImpl): InternalStorage = impl
}