package com.martin.medicationtracker.features.addsymptoms

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.martin.medicationtracker.R
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.databinding.ActivityAddSymptomsBinding
import com.martin.medicationtracker.models.SymptomSeverity
import kotlinx.android.synthetic.main.activity_add_medication.btnSave
import kotlinx.android.synthetic.main.activity_add_symptoms.*

class AddSymptomsActivity : BaseActivity() {

    private lateinit var viewModel: AddSymptomsViewModel
    private lateinit var binding: ActivityAddSymptomsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initView()
        setupEvents()
        observeChanges()
    }

    private fun setupBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_symptoms)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddSymptomsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initView() {
    }

    private fun setupEvents() {
        btnSave.setOnClickListener { saveSymptoms() }
        btnAddMore.setOnClickListener { viewModel.toggleAddMore() }
    }

    private fun saveSymptoms() {
        val otherSymptoms = etOthers.text.toString()
        val couchSeverity: SymptomSeverity = getCouchSeverity()
        val wheezeSeverity: SymptomSeverity = getWheezeSeverity()
        viewModel.saveSymptoms(otherSymptoms, couchSeverity, wheezeSeverity)
    }

    private fun getCouchSeverity(): SymptomSeverity {
        return when (rgCouch.checkedRadioButtonId) {
            R.id.rbCouchMild -> SymptomSeverity.MILD
            R.id.rbCouchModerate -> SymptomSeverity.MODERATE
            R.id.rbCouchSevere -> SymptomSeverity.SEVERE
            else -> SymptomSeverity.MILD
        }
    }

    private fun getWheezeSeverity(): SymptomSeverity {
        return when (rgWheeze.checkedRadioButtonId) {
            R.id.rbWheezeMild -> SymptomSeverity.MILD
            R.id.rbWheezeModerate -> SymptomSeverity.MODERATE
            R.id.rbWheezeSevere -> SymptomSeverity.SEVERE
            else -> SymptomSeverity.MILD
        }
    }

    private fun observeChanges() {

        viewModel.saveSuccess.observe(this, Observer {
            it?.let { success ->
                if (success) finish()
            }
        })
    }
}