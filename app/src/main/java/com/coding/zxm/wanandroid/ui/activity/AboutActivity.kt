package com.coding.zxm.wanandroid.ui.activity

import android.text.TextUtils
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivityAboutBinding
import com.zxm.utils.core.app.AppUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/2 . All rights reserved.
 */
class AboutActivity : BaseActivity() {

    private lateinit var aboutBinding: ActivityAboutBinding

    override fun setContentLayout(): Any {
       aboutBinding = ActivityAboutBinding.inflate(layoutInflater)
        return aboutBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()
    }

    override fun initViews() {
        aboutBinding.toolbar.tvToolbarTitle.text = getString(R.string.all_title_about)
        aboutBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        val versionName = AppUtil.getAppVersionName(mContext!!)
        val versionCode = AppUtil.getAppVersionCode(mContext!!)
        val versionInfo = "$versionName($versionCode)"

        aboutBinding.tvAboutVersion.text =
            if (!TextUtils.isEmpty(versionName))
                "${getString(R.string.all_abount_version)}${versionInfo}"
            else "版本：1.0.0"
    }
}