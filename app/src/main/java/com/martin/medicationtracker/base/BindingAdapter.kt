package com.martin.medicationtracker.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.graphics.Typeface
import android.widget.TextView



@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view).load(url).into(view)
}

@BindingAdapter("android:textStyle")
fun setTypeface(textView: TextView, style: String) {
    when (style) {
        "bold" -> textView.setTypeface(null, Typeface.BOLD)
        else -> textView.setTypeface(null, Typeface.NORMAL)
    }
}