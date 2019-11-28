package com.martin.medicationtracker.features.summary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.martin.medicationtracker.R
import com.martin.medicationtracker.databinding.ItemSummaryBinding
import com.martin.medicationtracker.databinding.ItemSummaryHeaderBinding
import com.martin.medicationtracker.models.Summary

class SummaryAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_ITEM = 2
    }

    private val summaries = ArrayList<Summary>()

    override fun getItemViewType(position: Int): Int {
        return if (summaries[position].isHeader) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding: ItemSummaryHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_summary_header, parent, false)
            SummaryHeaderViewHolder(binding)
        } else {
            val binding: ItemSummaryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_summary, parent, false)
            SummaryViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return summaries.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val summary = summaries[position]
        if (summaries[position].isHeader) {
            (holder as SummaryHeaderViewHolder).bind(summary)
        } else {
            (holder as SummaryViewHolder).bind(summary)
        }
    }

    fun swapData(summaries: List<Summary>) {
        this.summaries.clear()
        this.summaries.addAll(summaries)
        notifyDataSetChanged()
    }

    inner class SummaryHeaderViewHolder(private val binding: ItemSummaryHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(summary: Summary) {
            binding.tvName.text = summary.date
        }
    }

    inner class SummaryViewHolder(private val binding: ItemSummaryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(summary: Summary) {
            binding.tvTime.text = summary.time
            binding.tvValue.text = summary.value
        }
    }
}