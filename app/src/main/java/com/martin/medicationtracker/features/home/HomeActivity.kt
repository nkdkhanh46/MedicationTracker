package com.martin.medicationtracker.features.home

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.martin.medicationtracker.R
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.databinding.ActivityHomeBinding
import com.martin.medicationtracker.features.addmedication.AddMedicationActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupEvents()
        observeChanges()
    }

    private fun setupBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupEvents() {
        btnAddMedication.setOnClickListener { openAddMedicationScreen() }
        btnAddSymptoms.setOnClickListener {  }
        btnSummary.setOnClickListener {  }
    }

    private fun openAddMedicationScreen() {
        startActivity(Intent(this, AddMedicationActivity::class.java))
    }

    private fun observeChanges() {
        viewModel.user.observe(this, Observer {
            it?.let {
                viewModel.bindUserInformation(it)
            }
        })
    }
}