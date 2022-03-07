package com.coding.zxm.upgrade

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coding.zxm.upgrade.entity.UpdateResult
import com.coding.zxm.upgrade.network.IUpgradeProvider
import com.coding.zxm.upgrade.utils.UpgradeUtils


/**
 * Created by ZhangXinmin on 2021/05/17.
 * Copyright (c) 5/17/21 . All rights reserved.
 * 应用更新
 */
class UpgradeManager private constructor() {

    companion object {
        private const val TAG = "UpgradeManager"
        private var sInstance: UpgradeManager? = null
        private var config: UpgradeConfig? = null

        private var hasNewVersion: Boolean = false
        private var upgradeLivedata: MutableLiveData<UpdateResult> = MutableLiveData()

        @Synchronized
        fun getInstance(): UpgradeManager {
            if (sInstance == null) {
                sInstance = UpgradeManager()
            }
            return sInstance!!
        }

    }

    /**
     * 初始化
     */
    fun init(upgradeConfig: UpgradeConfig) {
        config = upgradeConfig
    }

    /**
     * 获取参数
     */
    fun getUpgradeConfig(): UpgradeConfig? {
        return config
    }

    /**
     * 检测版本更新
     * 返回LiveData为什么会多次回调
     */
    fun checkUpgrade(provider: IUpgradeProvider?) {
        if (config == null) {
            upgradeLivedata.postValue(UpdateResult(-1, "配置参数为空或未提供合适的下载器"))
            Log.e(TAG, "配置参数为空或未提供合适的下载器")
            return
        }
        provider?.let {
            UpgradeService.bindService(config?.context!!, object : ServiceConnection {
                override fun onServiceDisconnected(name: ComponentName?) {

                }

                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    (service as UpgradeService.UpgradeBinder).checkUpgrade(
                        it,
                        config?.upgradeToken,
                        config?.apkName
                    ).observeForever {
                        upgradeLivedata.postValue(it)
                    }
                }
            })
        }

        return
    }

    /**
     * 是否存在新版本
     */
    fun hasNewVersion(context: Context): Boolean {
        if (config != null && context != null) {
            val updateResult = upgradeLivedata.value
            if (updateResult?.code!! == 1) {
                val entity = updateResult.updateEntity
                if (entity != null) {
                    hasNewVersion = UpgradeUtils.hasNewVersion(context, entity)
                    return hasNewVersion
                }
            }
        }

        return false
    }

    /**
     * 下载新版本Apk
     */
    fun downloadApk(downloadUrl: String?, provider: IUpgradeProvider) {
        if (config == null || TextUtils.isEmpty(downloadUrl)) {
            Log.e(TAG, "配置参数为空")
            return
        }

        UpgradeService.bindService(config?.context!!, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as UpgradeService.UpgradeBinder).downloadApk(
                    downloadUrl,
                    provider
                )
            }

        })
    }

    class UpgradeConfig {
        var context: Context? = null
        var upgradeToken: String? = null
        var apkName: String? = null

        fun setContext(context: Context): UpgradeConfig {
            this.context = context
            return this
        }

        fun setUpgradeToken(token: String): UpgradeConfig {
            this.upgradeToken = token
            return this
        }

        fun setApkName(name: String): UpgradeConfig {
            apkName = name
            return this
        }

        fun create(): UpgradeConfig {
            if (context == null || TextUtils.isEmpty(upgradeToken)) {
                throw NullPointerException("关键参数为空！")
            }
            return this
        }
    }
}