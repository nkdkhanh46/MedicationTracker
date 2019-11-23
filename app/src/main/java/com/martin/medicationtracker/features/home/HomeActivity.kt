package com.martin.medicationtracker.features.home

import android.os.Bundle
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity

class HomeActivity : BaseActivity() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupEvents()
    }

    private fun setupBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
//        binding.viewModel = viewModel
//        binding.executePendingBindings()
    }

    private fun setupEvents() {
    }
}