package com.coding.zxm.util

import android.content.Context

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
class AppUtils private constructor() {

    companion object {

        /**
         * Get app version name
         */
        fun getAppVersionName(context: Context): String {
            val packageManager = context.packageManager
            if (packageManager != null) {
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionName
            }
            return ""
        }

        /**
         * Get app version code
         */
        fun getAppVersionCode(context: Context): Int {
            val packageManager = context.packageManager
            if (packageManager != null) {
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionCode
            }
            return -1
        }
    }

}