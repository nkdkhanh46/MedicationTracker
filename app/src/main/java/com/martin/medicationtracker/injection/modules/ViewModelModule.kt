package com.martin.medicationtracker.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martin.medicationtracker.features.addmedication.AddMedicationViewModel
import com.martin.medicationtracker.features.addsymptoms.AddSymptomsViewModel
import com.martin.medicationtracker.injection.ViewModelFactory
import com.martin.medicationtracker.injection.ViewModelKey
import com.martin.medicationtracker.features.home.HomeViewModel
import com.martin.medicationtracker.features.summary.SummaryViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(AddMedicationViewModel::class)
    abstract fun bindAddMedicationViewModel(viewModel: AddMedicationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddSymptomsViewModel::class)
    abstract fun bindAddSymptomsViewModel(viewModel: AddSymptomsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SummaryViewModel::class)
    abstract fun bindSummaryViewModel(viewModel: SummaryViewModel): ViewModel
}