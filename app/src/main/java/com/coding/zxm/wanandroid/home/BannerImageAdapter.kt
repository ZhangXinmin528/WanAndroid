package com.coding.zxm.wanandroid.home

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/13 . All rights reserved.
 */
class BannerImageAdapter(datas: MutableList<BannerEntity>?) :
    BannerAdapter<BannerEntity, BannerImageAdapter.ImageHolder>(datas) {

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView

        init {
            imageView = view as ImageView
        }
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent!!.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //通过裁剪实现圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BannerUtils.setBannerRound(imageView, 20f)
        }
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder?, data: BannerEntity?, position: Int, size: Int) {
        Glide.with(holder!!.itemView)
            .asBitmap()
            .load(data?.imagePath)
            .into(holder.imageView)
    }
}