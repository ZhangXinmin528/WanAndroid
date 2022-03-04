package com.coding.zxm.wanandroid.gallery

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.network.APIConstants
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivityImagePreviewBinding
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.image.model.GlideApp
import com.zxm.utils.core.screen.ScreenUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/12 . All rights reserved.
 */
class ImagePreviewActivity : BaseActivity() {

    companion object {
        private const val PARAMS_IMAGE_URL = "params_url"

        fun previewImage(context: Context, imageEntity: BingImageEntity) {
            val preview = Intent(context, ImagePreviewActivity::class.java)
            preview.putExtra(PARAMS_IMAGE_URL, imageEntity)
            context.startActivity(preview)
        }
    }

    private var mImageEntity: BingImageEntity? = null
    private lateinit var previewBinding: ActivityImagePreviewBinding

    override fun setContentLayout(): Any {
        previewBinding = ActivityImagePreviewBinding.inflate(layoutInflater)
        return previewBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarDark()
        mImageEntity = intent?.getSerializableExtra(PARAMS_IMAGE_URL) as BingImageEntity

        if (mImageEntity == null) {
            ToastUtil.showToast("未获取到图片资源")
            finish()
        }

    }

    override fun initViews() {

        GlideApp.with(previewBinding.ssivImage)
            .asBitmap()
            .load(APIConstants.BING_URL + mImageEntity?.url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    previewBinding.ssivImage.setImage(ImageSource.bitmap(resource))
                    if (previewBinding.ssivImage.isReady) {
                        val scale: Float =
                            ScreenUtil.getScreenHeight(mContext) / previewBinding.ssivImage.sHeight.toFloat()
                        val center =
                            PointF(0.5f * previewBinding.ssivImage.sWidth, 0.5f * previewBinding.ssivImage.sHeight)
                        val animationBuilder = previewBinding.ssivImage.animateScaleAndCenter(scale, center)
                        animationBuilder?.withDuration(4000)
                            ?.withEasing(SubsamplingScaleImageView.EASE_IN_OUT_QUAD)
                            ?.withInterruptible(false)?.start()
                    }
                }

            })

        previewBinding.ssivImage.setOnClickListener {
            finish()
        }
    }
}