package com.coding.zxm.wanandroid.setting

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.CacheUtil
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.R
import com.zxm.utils.core.sp.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    private val mLogoutViewModel: LogoutViewModel by viewModels { LogoutViewModel.LogoutViewModelFactory }

    override fun setLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent()
    }

    override fun initViews() {
        iv_toolbar_back.setOnClickListener(this)
        tv_setting_switch_language.setOnClickListener(this)
        tv_setting_font.setOnClickListener(this)
        layout_clear_cache.setOnClickListener(this)
        tv_setting_logout.setOnClickListener(this)

        tv_setting_cache_size.text = mContext?.let { CacheUtil.getAppCacheSize(it) }

        val logState = SharedPreferencesUtil.get(
            mContext!!,
            SPConfig.CONFIG_STATE_LOGIN,
            false
        ) as Boolean

        if (!logState) {
            tv_setting_logout.visibility = View.GONE
        }

        tv_toolbar_title.setText(R.string.all_setting)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_setting_switch_language -> {
                Toast.makeText(mContext, "开发小伙伴正加紧开发中...", Toast.LENGTH_SHORT).show()
            }
            R.id.iv_toolbar_back -> {
                finish()
            }
            R.id.tv_setting_font -> {
                jumpActivity(FontScaleActivity::class.java)
            }
            R.id.layout_clear_cache -> {
                mContext?.let {
                    CacheUtil.clearAppCache(it)
                    tv_setting_cache_size.text = CacheUtil.getAppCacheSize(it)
                }

            }
            R.id.tv_setting_logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        val liveData = mLogoutViewModel.logout()
        liveData.observe(this, Observer {
            if (it == 0) {

                Toast.makeText(
                    mContext,
                    getString(R.string.all_logout_succeess),
                    Toast.LENGTH_SHORT
                ).show()

                SharedPreferencesUtil.put(
                    mContext!!,
                    SPConfig.CONFIG_STATE_LOGIN,
                    false
                )
            }
        })
    }
}