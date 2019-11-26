package com.martin.medicationtracker.features.addmedication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.martin.medicationtracker.R
import com.martin.medicationtracker.application.MainApplication
import com.martin.medicationtracker.base.BaseActivity
import com.martin.medicationtracker.databinding.ActivityAddMedicationBinding
import com.martin.medicationtracker.features.addmedication.orccapture.OcrCaptureActivity
import com.martin.medicationtracker.utils.Constants
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_add_medication.*

class AddMedicationActivity : BaseActivity() {

    private lateinit var viewModel: AddMedicationViewModel
    private lateinit var binding: ActivityAddMedicationBinding
    private var timeAdapter: TimeAdapter? = null
    private var medicationAdapter: MedicationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        initView()
        setupEvents()
        observeChanges()
    }

    private fun setupBinding() {
        (application as MainApplication?)?.appComponent?.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_medication)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddMedicationViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initView() {
        val name = intent.getStringExtra(Constants.INTENT_OCR_TEXT)
        etMedicationName.setText(name)
        viewModel.updateMedicationName(name?:"")
        setupTimesList()
        setupMedicationsList()
    }

    private fun setupTimesList() {
        timeAdapter = TimeAdapter()
        val lm = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTimes.apply {
            adapter = timeAdapter
            layoutManager = lm
        }
    }

    private fun setupMedicationsList() {
        medicationAdapter = MedicationAdapter()
        val lm = LinearLayoutManager(this)
        rvMedications.apply {
            adapter = medicationAdapter
            layoutManager = lm
        }
    }

    private fun setupEvents() {
        btnSave.setOnClickListener { viewModel.saveMedication() }

        btnIncreaseFrequency.setOnClickListener { viewModel.increaseFrequency() }
        btnDecreaseFrequency.setOnClickListener { viewModel.decreaseFrequency() }
        btnIncreaseDose.setOnClickListener { viewModel.increaseDose() }
        btnDecreaseDose.setOnClickListener { viewModel.decreaseDose() }
        tvBefore.setOnClickListener { viewModel.updateBeforeMeal(true) }
        tvAfter.setOnClickListener { viewModel.updateBeforeMeal(false) }
        tvDate.setOnClickListener { showDatePicker() }
        btnAddTime.setOnClickListener { showTimePicker() }
        etMedicationName.doAfterTextChanged { viewModel.updateMedicationName(it?.toString()?:"") }
        btnAddMedication.setOnClickListener {
            viewModel.createNewMedication()
            openMedicationNameReader()
        }
    }

    private fun showDatePicker() {
        val dialog = DatePickerDialog.newInstance { _, year, month, date ->
            viewModel.updateDoctorVisitDate(year, month, date)
        }
        dialog.show(supportFragmentManager, "DatePickerDialog")
    }

    private fun showTimePicker() {
        val dialog = TimePickerDialog.newInstance({ _, hourOfDay, minute, _ ->
            viewModel.addTime(hourOfDay, minute) },
            true
        )
        dialog.show(supportFragmentManager, "TimePickerDialog")
    }

    private fun openMedicationNameReader() {
        startActivityForResult(Intent(this, OcrCaptureActivity::class.java), Constants.REQUEST_CODE_OCR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == Constants.REQUEST_CODE_OCR) {
            val name = data?.getStringExtra(Constants.INTENT_OCR_TEXT) ?: return
            etMedicationName.setText(name)
        }
    }

    private fun observeChanges() {
        viewModel.times.observe(this, Observer {
            it?.let { times ->
                timeAdapter?.swapData(times)
            }
        })
        viewModel.medications.observe(this, Observer {
            it?.let { medications ->
                medicationAdapter?.swapData(medications)
            }
        })

        viewModel.notification.observe(this, Observer {
            it?.let { notification ->
                when (notification) {
                    AddMedicationViewModel.NOTIFICATION_NAME_EMPTY -> showToastMessage(R.string.message_medication_name_empty)
                    AddMedicationViewModel.NOTIFICATION_VISIT_DATE_EMPTY -> showToastMessage(R.string.message_medication_visit_date_empty)
                    AddMedicationViewModel.NOTIFICATION_TIMES_EMPTY -> showToastMessage(R.string.message_medication_times_empty)
                }

                viewModel.notification.value = null
            }
        })
    }

    private fun showToastMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}