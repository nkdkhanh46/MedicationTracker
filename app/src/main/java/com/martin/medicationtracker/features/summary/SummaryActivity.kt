package com.martin.medicationtracker.features.summary

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.martin.medicationtracker.R
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.databinding.ActivitySummaryBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : BaseActivity() {

    private lateinit var viewModel: SummaryViewModel
    private lateinit var binding: ActivitySummaryBinding
    private var summaryAdapter: SummaryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initViews()
        observeChanges()
    }

    private fun setupBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_summary)
        viewModel = ViewModelProvider(this, viewModelFactory)[SummaryViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initViews() {
        summaryAdapter = SummaryAdapter()
        val lm = LinearLayoutManager(this)
        rvSummary.apply {
            adapter = summaryAdapter
            layoutManager = lm
        }
    }

    private fun observeChanges() {
        viewModel.summaries.observe(this, Observer {
            it?.let { summaries ->
                summaryAdapter?.swapData(summaries)
            }
        })
    }
}