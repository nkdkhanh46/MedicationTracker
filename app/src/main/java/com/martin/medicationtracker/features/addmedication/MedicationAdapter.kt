package com.martin.medicationtracker.features.addmedication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martin.medicationtracker.R
import com.martin.medicationtracker.databinding.ItemMedicationBinding
import com.martin.medicationtracker.models.Medication

class MedicationAdapter: RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    private val medications = ArrayList<Medication>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val binding: ItemMedicationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_medication, parent, false)
        return MedicationViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return medications.size
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        holder.bind(medications[position])
    }

    fun swapData(medications: List<Medication>) {
        this.medications.clear()
        this.medications.addAll(medications)
        notifyDataSetChanged()
    }

    inner class MedicationViewHolder(private val binding: ItemMedicationBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(medication: Medication) {
            binding.tvName.text = medication.name
        }

    }
}