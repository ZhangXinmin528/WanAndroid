package com.coding.zxm.wanandroid.ui.activity

import android.util.Log
import android.util.TypedValue
import android.view.View
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.SharedPreferenceConfig
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.ui.widget.FontSeekbar
import com.zxm.utils.core.sp.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_font_scale.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/22 . All rights reserved.
 * 设置字体大小
 * TODO:存在bug
 */
class FontScaleActivity : BaseActivity(), View.OnClickListener {

    override fun setLayoutId(): Int {
        return R.layout.activity_font_scale
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent()
    }

    override fun initViews() {
        iv_font_back.setOnClickListener(this)
        tv_font_confirm.setOnClickListener(this)
        val scale =
            SharedPreferencesUtil.get(
                mContext!!,
                SharedPreferenceConfig.CONFIG_FONT_SCALE,
                1.0f
            ) as Float

        Log.d("zxm==", "初始化：$scale")

        font_seekbar.setScaleValue(scale = scale)
        font_seekbar.setOnScaleCallback(object : FontSeekbar.OnScaleCallback {
            override fun onScale(scale: Float) {
                Log.d("zxm==", "设置：$scale")
                tv_font_example.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 * scale)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_font_back -> {
                finish()
            }
            R.id.tv_font_confirm -> {
                SharedPreferencesUtil.put(
                    mContext!!,
                    SharedPreferenceConfig.CONFIG_FONT_SCALE,
                    font_seekbar.getScaleValue()
                )
                finish()
            }
        }
    }
}