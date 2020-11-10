package com.coding.zxm.wanandroid.gallery

import android.graphics.PointF
import android.view.View
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import kotlinx.android.synthetic.main.fragment_image_display.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/10 . All rights reserved.
 */
class ImageDisplayFragment : BaseFragment() {

    companion object {
        fun newInstance(): ImageDisplayFragment {
            return ImageDisplayFragment()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_image_display
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent(R.color.colorAccent)

    }

    override fun initViews(rootView: View) {
        ssiv_image.setImage(ImageSource.asset("bing_example.jpg"))

        ssiv_image.setOnClickListener {
            if (ssiv_image.isReady) {
                val maxScale: Float = ssiv_image.maxScale
                val minScale: Float = ssiv_image.minScale
                val scale: Float = 0.5f * (maxScale - minScale) + minScale
                val center =
                    PointF(0.5f * ssiv_image.sWidth, 0.5f * ssiv_image.sHeight)
                val animationBuilder = ssiv_image.animateScaleAndCenter(scale, center)
                animationBuilder?.withDuration(2000)
                    ?.withEasing(SubsamplingScaleImageView.EASE_IN_OUT_QUAD)
                    ?.withInterruptible(false)?.start()
            }
        }

    }
}