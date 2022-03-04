package com.coding.zxm.wanandroid.setting

import android.content.Intent
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.MainActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivityFontScaleBinding
import com.coding.zxm.wanandroid.ui.widget.FontSeekbar
import com.zxm.utils.core.sp.SharedPreferencesUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/22 . All rights reserved.
 * 设置字体大小
 * TODO:自定义控件点击不太灵敏
 */
class FontScaleActivity : BaseActivity(), View.OnClickListener {
    private lateinit var fontScaleBinding: ActivityFontScaleBinding

    override fun setContentLayout(): Any {
        fontScaleBinding = ActivityFontScaleBinding.inflate(layoutInflater)
        return fontScaleBinding.root
    }


    override fun initParamsAndValues() {
        setStatusBarColorLight()
    }

    override fun initViews() {
        fontScaleBinding.toolbar.ivToolbarBack.setOnClickListener(this)
        fontScaleBinding.toolbar.tvToolbarConfirm.setOnClickListener(this)
        fontScaleBinding.toolbar.tvToolbarConfirm.visibility = View.VISIBLE
        fontScaleBinding.toolbar.tvToolbarTitle.text = getString(R.string.all_setting_font_size)
        val scale =
            SharedPreferencesUtil.get(
                mContext!!,
                SPConfig.CONFIG_FONT_SCALE,
                1.0f
            ) as Float

        Log.d("zxm==", "初始化：$scale")

        fontScaleBinding.fontSeekbar.setScaleValue(scale = scale)
        fontScaleBinding.fontSeekbar.setOnScaleCallback(object : FontSeekbar.OnScaleCallback {
            override fun onScale(scale: Float) {
                Log.d("zxm==", "设置：$scale")
                fontScaleBinding.tvFontExample.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14 * scale)
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                finish()
            }
            R.id.tv_toolbar_confirm -> {
                SharedPreferencesUtil.put(
                    mContext!!,
                    SPConfig.CONFIG_FONT_SCALE,
                    fontScaleBinding.fontSeekbar.getScaleValue()
                )
                finish()

                val intent = Intent(mContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}