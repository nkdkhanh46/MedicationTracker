package com.martin.medicationtracker.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martin.medicationtracker.R
import com.martin.medicationtracker.databinding.ItemTutorialImageBinding
import com.martin.medicationtracker.models.Tutorial

class ImageAdapter(private val screenWidth: Int, images: List<Tutorial>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private val images = ArrayList<Tutorial>()

    init {
        if (images.isNotEmpty()) {
            this.images.add(images[images.size-1])
            this.images.addAll(images)
            this.images.add(images[0])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding: ItemTutorialImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_tutorial_image, parent, false
        )
        binding.root.layoutParams.width = (screenWidth*0.7).toInt()
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    class ImageViewHolder(private val binding: ItemTutorialImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tutorial: Tutorial) {
            Glide.with(itemView).load(tutorial.image).into(binding.ivTutorial)
        }
    }
}