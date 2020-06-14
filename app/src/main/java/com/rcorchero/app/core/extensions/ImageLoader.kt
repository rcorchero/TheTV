package com.rcorchero.app.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadFromUrl(url: String, placeholder: Int) =
    Glide.with(this.context.applicationContext)
        .load(url)
        .placeholder(placeholder)
        .into(this)