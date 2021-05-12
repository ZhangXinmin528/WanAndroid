package com.coding.zxm.webview

import android.content.Context
import android.util.Log
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsListener


/**
 * Created by ZhangXinmin on 2021/05/11.
 * Copyright (c) 5/11/21 . All rights reserved.
 */
class TbsManager private constructor(val context: Context) {

    companion object {
        private const val TAG = "TbsManager"
        private var sInstance: TbsManager? = null

        fun getInstance(context: Context): TbsManager? {
            if (sInstance == null) {
                sInstance = TbsManager(context)
            }
            return sInstance
        }

    }

    fun initTBS() {
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        QbSdk.initX5Environment(context, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                Log.d(TAG, "x5内核初始化完成！")
            }

            override fun onViewInitFinished(p0: Boolean) {

            }

        })

        QbSdk.setTbsListener(object : TbsListener {
            override fun onInstallFinish(p0: Int) {
                Log.d(TAG, "x5内核安装成功！")
            }

            override fun onDownloadFinish(p0: Int) {
                Log.d(TAG, "x5内核下载成功！")
            }

            override fun onDownloadProgress(p0: Int) {
                TODO("Not yet implemented")
            }

        })

    }
}