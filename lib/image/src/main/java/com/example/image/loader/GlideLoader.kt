package com.example.image.loader

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 */
class GlideLoader private constructor() {

    companion object {
        val INSTANCE: GlideLoader = Holder.holder
    }

    private object Holder {
        val holder = GlideLoader()
    }

    fun loadImage(target: ImageView, bitmap: Bitmap) {
        Glide.with(target)
            .load(bitmap)
            .into(target)
    }

    fun loadImage(target: ImageView, @DrawableRes resId: Int) {
        Glide.with(target)
            .load(resId)
            .into(target)
    }


}