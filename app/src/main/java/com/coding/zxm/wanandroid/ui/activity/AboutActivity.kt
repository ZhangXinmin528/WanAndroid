package com.coding.zxm.wanandroid.ui.activity

import android.text.TextUtils
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.zxm.utils.core.app.AppUtil
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/2 . All rights reserved.
 */
class AboutActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()
    }

    override fun initViews() {
        tv_toolbar_title.text = getString(R.string.all_title_about)
        iv_toolbar_back.setOnClickListener { finish() }

        val versionName = AppUtil.getAppVersionName(mContext!!)
        val versionCode = AppUtil.getAppVersionCode(mContext!!)
        val versionInfo = "$versionName($versionCode)"

        tv_about_version.text =
            if (!TextUtils.isEmpty(versionName))
                "${getString(R.string.all_abount_version)}${versionInfo}"
            else "版本：1.0.0"
    }
}