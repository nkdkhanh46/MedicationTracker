package com.martin.medicationtracker.features.addmedication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martin.medicationtracker.R
import com.martin.medicationtracker.databinding.ItemTimeBinding

class TimeAdapter: RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {

    val times = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val binding: ItemTimeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_time, parent, false)
        return TimeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return times.size
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(times[position])
    }

    fun swapData(times: List<String>) {
        this.times.clear()
        this.times.addAll(times)
        notifyDataSetChanged()
    }

    inner class TimeViewHolder(private val binding: ItemTimeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(time: String) {
            binding.tvTime.text = time
        }

    }
}