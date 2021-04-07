package com.msc.dogsapplication.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.msc.dogsapplication.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 30f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progress: CircularProgressDrawable) {
    val rOptions = RequestOptions().placeholder(progress).error(R.mipmap.ic_dog_icon)
    Glide.with(context).setDefaultRequestOptions(rOptions).load(uri).into(this)
}