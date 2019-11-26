package com.martin.medicationtracker.injection.modules

import android.content.Context
import androidx.room.Room
import com.martin.medicationtracker.database.AppDatabase
import com.martin.medicationtracker.database.MedicationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
//            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideMedicationDao(appDatabase: AppDatabase): MedicationDao {
        return appDatabase.medicationDao()
    }
}