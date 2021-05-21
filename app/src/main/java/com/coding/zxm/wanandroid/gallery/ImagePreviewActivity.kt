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
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.example.image.model.GlideApp
import com.zxm.utils.core.screen.ScreenUtil
import kotlinx.android.synthetic.main.activity_image_preview.*

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

    override fun setLayoutId(): Int {
        return R.layout.activity_image_preview
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

        GlideApp.with(ssiv_image)
            .asBitmap()
            .load(APIConstants.BING_URL + mImageEntity?.url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    ssiv_image.setImage(ImageSource.bitmap(resource))
                    if (ssiv_image.isReady) {
                        val scale: Float =
                            ScreenUtil.getScreenHeight(mContext) / ssiv_image.sHeight.toFloat()
                        val center =
                            PointF(0.5f * ssiv_image.sWidth, 0.5f * ssiv_image.sHeight)
                        val animationBuilder = ssiv_image.animateScaleAndCenter(scale, center)
                        animationBuilder?.withDuration(4000)
                            ?.withEasing(SubsamplingScaleImageView.EASE_IN_OUT_QUAD)
                            ?.withInterruptible(false)?.start()
                    }
                }

            })

        ssiv_image.setOnClickListener {
            finish()
        }
    }
}