package com.martin.medicationtracker.injection.modules

import com.martin.medicationtracker.repositories.DataRepository
import com.martin.medicationtracker.repositories.UserRepository
import com.martin.medicationtracker.repositories.impl.DataRepositoryImpl
import com.martin.medicationtracker.repositories.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository = impl

    @Provides
    fun provideDataRepository(impl: DataRepositoryImpl): DataRepository = impl
}