package com.coding.zxm.umeng.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.umeng.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/7 . All rights reserved.
 */
class ImageShareActivity : BaseActivity() {

    companion object {
        private const val PARAMS_VIEW = "params_view"

        fun doImageShare(context: Context, target: Bitmap) {
            val intent = Intent(context, ImageShareActivity::class.java)
            intent.putExtra(PARAMS_VIEW, target)
            context.startActivity(intent)
        }
    }

    private lateinit var mTarget: Bitmap

    override fun setLayoutId(): Int {
        return R.layout.activity_image_share
    }

    override fun initParamsAndValues() {

        if (intent != null) {
            mTarget = intent.getParcelableExtra(PARAMS_VIEW)
        }
    }

    override fun initViews() {

    }
}