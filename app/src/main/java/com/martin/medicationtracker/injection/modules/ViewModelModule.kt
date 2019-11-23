package com.martin.medicationtracker.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martin.medicationtracker.injection.ViewModelFactory
import com.martin.medicationtracker.injection.ViewModelKey
import com.martin.medicationtracker.features.home.HomeViewModel
import com.martin.medicationtracker.features.tutorial.TutorialViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel::class)
    abstract fun bindTutorialViewModel(viewModel: TutorialViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}