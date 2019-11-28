package com.martin.medicationtracker.features.summary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martin.medicationtracker.R
import com.martin.medicationtracker.databinding.ItemGridMedicationBinding
import com.martin.medicationtracker.models.Medication
import com.martin.medicationtracker.models.MedicationStatus

class SummaryAdapter: RecyclerView.Adapter<SummaryAdapter.MedicationViewHolder>() {

    interface Listener {
        fun onConfirmTaken(medication: MedicationStatus)
    }

    private val medications = ArrayList<MedicationStatus>()
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding: ItemGridMedicationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_grid_medication, parent, false)
        return MedicationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return medications.size
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(medications[position])
    }

    fun swapData(medications: List<MedicationStatus>) {
        this.medications.clear()
        this.medications.addAll(medications)
        notifyDataSetChanged()
    }

    inner class MedicationViewHolder(private val binding: ItemGridMedicationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(medication: MedicationStatus) {
            binding.tvName.text = medication.name
            binding.tvDose.text = String.format("%s %s", medication.dose, medication.form)
            binding.tvFrequency.text = String.format(getString(R.string.medication_frequency), medication.frequency)
            val time = if (medication.beforeMeal) R.string.medication_before_meal else R.string.medication_after_meal
            binding.tvTime.text = getString(time)
            binding.btnConfirmTaken.visibility = if (medication.taken > 0) View.GONE else View.VISIBLE
            binding.ivDone.visibility = if (medication.taken > 0) View.VISIBLE else View.GONE

            binding.btnConfirmTaken.setOnClickListener {
                if (medication.taken > 0) return@setOnClickListener

                listener?.onConfirmTaken(medication)
            }
        }

        private fun getString(res: Int): String {
            return itemView.context.getString(res)
        }
    }
}