package com.example.image.loader

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.example.image.R
import com.example.image.model.GlideApp

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 */
class ImageLoader private constructor() {

    companion object {
        val INSTANCE: ImageLoader = Holder.holder
    }

    private object Holder {
        val holder = ImageLoader()
    }

    fun loadBitmap(target: ImageView, bitmap: Bitmap) {
        GlideApp.with(target)
            .asBitmap()
            .load(bitmap)
            .placeholder(R.drawable.icon_image_holder)
            .into(target)
    }

    fun loadImageRes(target: ImageView, filePath: String) {
        GlideApp.with(target)
            .asBitmap()
            .load(filePath)
            .placeholder(R.drawable.icon_image_holder)
            .into(target)
    }

    fun loadImage(target: ImageView, @DrawableRes resId: Int) {
        Glide.with(target)
            .load(resId)
            .into(target)
    }


}