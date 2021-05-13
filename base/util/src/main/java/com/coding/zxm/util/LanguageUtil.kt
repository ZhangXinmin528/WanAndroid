package com.coding.zxm.util

import android.content.Context
import android.text.TextUtils
import com.zxm.utils.core.sp.SharedPreferencesUtil
import java.util.*

/**
 * Created by ZhangXinmin on 2020/11/29.
 * Copyright (c) 2020 . All rights reserved.
 */
class LanguageUtil private constructor() {

    companion object {

        /**
         * 获取和风天气语言环境配置
         */
        fun getLanguageConfig(context: Context): String {
            return SharedPreferencesUtil.get(context, SPConfig.CONFIG_APP_LANGUAGE, "") as String
        }

        /**
         * 为页面设置语言
         */
        fun setLanguageConfig(context: Context) {
            val resources = context.resources
            val config = resources.configuration
            var locale: Locale? = null
            val language =
                SharedPreferencesUtil.get(context, SPConfig.CONFIG_APP_LANGUAGE, "") as String
            if (TextUtils.isEmpty(language))
                return

            locale = when (language) {
                "cn" -> {
                    Locale.SIMPLIFIED_CHINESE
                }
                "en" -> {
                    Locale.ENGLISH
                }
                else -> {
                    Locale.getDefault()
                }
            }
            if (locale != null) {
                config.locale = locale
                context.resources.updateConfiguration(config, resources.displayMetrics)
            }

        }

        /**
         * 获取选取语言的名称
         */
        fun getSettingLanguageName(context: Context): String {
            val index = getSettingLanguagePosition(context)
            val nameArray = context.resources.getStringArray(R.array.array_lan_name)
            return if (index == -1) {
                nameArray[0]
            } else {
                nameArray[index]
            }
        }

        /**
         * 获取选取语言的位置
         */
        fun getSettingLanguagePosition(context: Context): Int {
            val lan = SharedPreferencesUtil.get(context, SPConfig.CONFIG_APP_LANGUAGE, "") as String
            val lanArray = context.resources.getStringArray(R.array.array_lan)
            return if (TextUtils.isEmpty(lan)) {
                0
            } else {
                lanArray.indexOf(lan)
            }
        }
    }
}