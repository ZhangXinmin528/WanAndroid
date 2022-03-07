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
import com.coding.zxm.wanandroid.databinding.ActivitySettingBinding
import com.zxm.utils.core.app.AppUtil
import com.zxm.utils.core.cache.CacheUtil
import com.zxm.utils.core.sp.SharedPreferencesUtil

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    private val mLogoutViewModel: LogoutViewModel by viewModels { LogoutViewModel.LogoutViewModelFactory }

    private lateinit var mProvider: IUpgradeProvider
    private lateinit var settingBinding: ActivitySettingBinding

    override fun setContentLayout(): Any {
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        return settingBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()

        mProvider = UpgradeProgressProvider(this)
    }

    override fun initViews() {

        settingBinding.toolbar.tvToolbarTitle.setText(R.string.all_setting)

        settingBinding.toolbar.ivToolbarBack.setOnClickListener(this)
        settingBinding.tvSettingSwitchLanguage.setOnClickListener(this)
        settingBinding.tvSettingSwitchLanguage.text =
            LanguageUtil.getSettingLanguageName(mContext!!)

        settingBinding.tvSettingFont.setOnClickListener(this)
        settingBinding.layoutClearCache.setOnClickListener(this)
        settingBinding.tvSettingLogout.setOnClickListener(this)
        settingBinding.tvSettingNewVersion.setOnClickListener(this)

        settingBinding.tvSettingCacheSize.text = mContext?.let { CacheUtil.getAppCacheSize(it) }

        //调试模式
        //网络调试
        val networkState =
            SharedPreferencesUtil.get(
                mContext!!,
                SPConfig.CONFIG_SETTING_NETWORK_STATE,
                false
            ) as Boolean
        settingBinding.tvSettingResultNetwork.visibility =
            if (networkState) View.VISIBLE else View.GONE

        settingBinding.switchSettingNetwork.isChecked = networkState
        settingBinding.switchSettingNetwork.setOnCheckedChangeListener { buttonView, isChecked ->
            settingBinding.tvSettingResultNetwork.visibility =
                if (isChecked) View.VISIBLE else View.GONE
            SharedPreferencesUtil.put(mContext!!, SPConfig.CONFIG_SETTING_NETWORK_STATE, isChecked)
        }

        settingBinding.tvSettingResultNetwork.setOnClickListener(this)

        val logState = SharedPreferencesUtil.get(
            mContext!!,
            SPConfig.CONFIG_STATE_LOGIN,
            false
        ) as Boolean

        if (!logState) {
            settingBinding.tvSettingLogout.visibility = View.GONE
        }

        val state = UpgradeManager.getInstance().hasNewVersion(this)
        settingBinding.tvSettingVersionTag.visibility = if (state) View.VISIBLE else View.GONE

        settingBinding.tvSettingCurrVersion.text = AppUtil.getAppVersionName(mContext!!)
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
                    settingBinding.tvSettingCacheSize.text = CacheUtil.getAppCacheSize(it)
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