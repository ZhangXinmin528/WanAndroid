package com.coding.zxm.wanandroid.setting

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.upgrade.UpgradeManager
import com.coding.zxm.upgrade.network.IUpgradeProvider
import com.coding.zxm.upgrade.network.UpgradeProgressProvider
import com.coding.zxm.util.LanguageUtil
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.app.WanApp
import com.zxm.utils.core.app.AppUtil
import com.zxm.utils.core.cache.CacheUtil
import com.zxm.utils.core.sp.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    private val mLogoutViewModel: LogoutViewModel by viewModels { LogoutViewModel.LogoutViewModelFactory }

    private lateinit var mProvider: IUpgradeProvider

    override fun setLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initParamsAndValues() {
        setStatusBarColorWhite()

        mProvider = UpgradeProgressProvider(this)
    }

    override fun initViews() {

        tv_toolbar_title.setText(R.string.all_setting)

        iv_toolbar_back.setOnClickListener(this)
        tv_setting_switch_language.setOnClickListener(this)
        tv_setting_switch_language.text = LanguageUtil.getSettingLanguageName(mContext!!)

        tv_setting_font.setOnClickListener(this)
        layout_clear_cache.setOnClickListener(this)
        tv_setting_logout.setOnClickListener(this)
        tv_setting_new_version.setOnClickListener(this)

        tv_setting_cache_size.text = mContext?.let { CacheUtil.getAppCacheSize(it) }

        //调试模式
        //网络调试
        val networkState =
            SharedPreferencesUtil.get(
                mContext!!,
                SPConfig.CONFIG_SETTING_NETWORK_STATE,
                false
            ) as Boolean
        tv_setting_result_network.visibility = if (networkState) View.VISIBLE else View.GONE

        switch_setting_network.isChecked = networkState
        switch_setting_network.setOnCheckedChangeListener { buttonView, isChecked ->
            tv_setting_result_network.visibility = if (isChecked) View.VISIBLE else View.GONE
            SharedPreferencesUtil.put(mContext!!, SPConfig.CONFIG_SETTING_NETWORK_STATE, isChecked)
        }

        tv_setting_result_network.setOnClickListener(this)

        val logState = SharedPreferencesUtil.get(
            mContext!!,
            SPConfig.CONFIG_STATE_LOGIN,
            false
        ) as Boolean

        if (!logState) {
            tv_setting_logout.visibility = View.GONE
        }

        UpgradeManager.getInstance().hasNewVersion(mProvider).observe(this, Observer { state ->
            tv_setting_version_tag.visibility = if (state) View.VISIBLE else View.GONE
        })


        tv_setting_curr_version.text = AppUtil.getAppVersionName(mContext!!)
    }

    private fun checkUpgrade() {
        UpgradeManager.getInstance()
            .checkUpgrade(mProvider)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                finish()
            }
            R.id.tv_setting_switch_language -> {
                jumpActivity(LanguagesSettingActivity::class.java)
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
            R.id.tv_setting_new_version -> {
                checkUpgrade()
            }
            R.id.tv_setting_logout -> {
                logout()
            }
            R.id.tv_setting_result_network -> {
                RetrofitClient.getInstance(WanApp.getApplicationContext()).toChuck(mContext!!)
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

                finish()
            }
        })
    }

}