package com.coding.zxm.weather.util

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import com.coding.zxm.util.SPConfig
import com.coding.zxm.weather.R
import com.qweather.sdk.bean.base.IndicesType
import com.qweather.sdk.bean.base.Lang
import com.zxm.utils.core.sp.SharedPreferencesUtil
import java.util.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/4 . All rights reserved.
 */
class WeatherUtil private constructor() {
    companion object {
        /**
         * 判断是白天还是黑夜
         * <p>
         *     返回true，是白天；反之黑夜；
         */
        fun isInDayOrNight(): Boolean {
            return Calendar.getInstance().get(Calendar.HOUR_OF_DAY) in 6..18
        }

        /**
         * 获取和风天气对用语言参数
         */
        fun getQWeatherLanCode(context: Context): Lang {
            val language =
                SharedPreferencesUtil.get(context, SPConfig.CONFIG_APP_LANGUAGE, "") as String
            if (TextUtils.isEmpty(language))
                return Lang.ZH_HANS

            return when (language) {
                "zh" -> {
                    Lang.ZH_HANS
                }
                "en" -> {
                    Lang.EN
                }
                else -> {
                    Lang.ZH_HANS
                }
            }
        }

        /**
         * 获取空气适量AQI对应级别颜色资源
         */
        fun getAQIColorRes(level: String): Int {

            when (level) {
                "1" -> {
                    return R.color.color_air_excellent
                }
                "2" -> {
                    return R.color.color_air_good
                }
                "3" -> {
                    return R.color.color_air_low
                }
                "4" -> {
                    return R.color.color_air_mid
                }
                "5" -> {
                    return R.color.color_air_bad
                }
                "6" -> {
                    return R.color.color_air_serious
                }
                else -> {
                    return Color.WHITE
                }
            }
        }

        /**
         * 获取空气适量AQI-PM2.5对应级别背景
         */
        fun getAQIPM2_5DrawableRes(value: String): Int {
            when {
                value.toFloat() in 0.0..35.0 -> {
                    return R.drawable.shape_aqi_excellent
                }
                value.toFloat() in 36.0..75.0 -> {
                    return R.drawable.shape_aqi_good
                }
                value.toFloat() in 76.0..115.0 -> {
                    return R.drawable.shape_aqi_low
                }
                value.toFloat() in 116.0..150.0 -> {
                    return R.drawable.shape_aqi_mid
                }
                value.toFloat() in 151.0..250.0 -> {
                    return R.drawable.shape_aqi_bad
                }
                value.toFloat() > 251.0 -> {
                    return R.drawable.shape_aqi_serious
                }
                else -> {
                    return -1
                }
            }
        }

        /**
         * 获取空气适量AQI-PM10对应级别背景
         */
        fun getAQIPM10DrawableRes(value: String): Int {
            when {
                value.toFloat() in 0.0..50.0 -> {
                    return R.drawable.shape_aqi_excellent
                }
                value.toFloat() in 51.0..150.0 -> {
                    return R.drawable.shape_aqi_good
                }
                value.toFloat() in 151.0..250.0 -> {
                    return R.drawable.shape_aqi_low
                }
                value.toFloat() in 251.0..350.0 -> {
                    return R.drawable.shape_aqi_mid
                }
                value.toFloat() in 351.0..420.0 -> {
                    return R.drawable.shape_aqi_bad
                }
                value.toFloat() > 421.0 -> {
                    return R.drawable.shape_aqi_serious
                }
                else -> {
                    return -1
                }
            }
        }

        /**
         * 获取空气适量AQI-SO2对应级别背景
         */
        fun getAQISO2DrawableRes(value: String): Int {
            when {
                value.toFloat() in 0.0..150.0 -> {
                    return R.drawable.shape_aqi_excellent
                }
                value.toFloat() in 151.0..500.0 -> {
                    return R.drawable.shape_aqi_good
                }
                value.toFloat() in 501.0..650.0 -> {
                    return R.drawable.shape_aqi_low
                }
                value.toFloat() in 651.0..800.0 -> {
                    return R.drawable.shape_aqi_mid
                }
                value.toFloat() in 801.0..1600.0 -> {
                    return R.drawable.shape_aqi_bad
                }
                value.toFloat() > 1601.0 -> {
                    return R.drawable.shape_aqi_serious
                }
                else -> {
                    return -1
                }
            }
        }

        /**
         * 获取空气适量AQI-O3对应级别背景
         */
        fun getAQIO3DrawableRes(value: String): Int {
            when {
                value.toFloat() in 0.0..160.0 -> {
                    return R.drawable.shape_aqi_excellent
                }
                value.toFloat() in 161.0..200.0 -> {
                    return R.drawable.shape_aqi_good
                }
                value.toFloat() in 201.0..300.0 -> {
                    return R.drawable.shape_aqi_low
                }
                value.toFloat() in 301.0..400.0 -> {
                    return R.drawable.shape_aqi_mid
                }
                value.toFloat() in 401.0..800.0 -> {
                    return R.drawable.shape_aqi_bad
                }
                value.toFloat() > 801.0 -> {
                    return R.drawable.shape_aqi_serious
                }
                else -> {
                    return -1
                }
            }
        }

        /**
         * 获取空气适量AQI-CO对应级别背景
         */
        fun getAQICODrawableRes(value: String): Int {
            when {
                value.toFloat() in 0.0..5.0 -> {
                    return R.drawable.shape_aqi_excellent
                }
                value.toFloat() in 6.0..10.0 -> {
                    return R.drawable.shape_aqi_good
                }
                value.toFloat() in 11.0..35.0 -> {
                    return R.drawable.shape_aqi_low
                }
                value.toFloat() in 36.0..60.0 -> {
                    return R.drawable.shape_aqi_mid
                }
                value.toFloat() in 61.0..90.0 -> {
                    return R.drawable.shape_aqi_bad
                }
                value.toFloat() > 91.0 -> {
                    return R.drawable.shape_aqi_serious
                }
                else -> {
                    return -1
                }
            }
        }

        /**
         * 获取空气适量AQI-NO2对应级别背景
         */
        fun getAQINO2DrawableRes(value: String): Int {
            when {
                value.toFloat() in 0.0..100.0 -> {
                    return R.drawable.shape_aqi_excellent
                }
                value.toFloat() in 101.0..200.0 -> {
                    return R.drawable.shape_aqi_good
                }
                value.toFloat() in 201.0..700.0 -> {
                    return R.drawable.shape_aqi_low
                }
                value.toFloat() in 701.0..1200.0 -> {
                    return R.drawable.shape_aqi_mid
                }
                value.toFloat() in 1201.0..2340.0 -> {
                    return R.drawable.shape_aqi_bad
                }
                value.toFloat() > 2341.0 -> {
                    return R.drawable.shape_aqi_serious
                }
                else -> {
                    return -1
                }
            }
        }

        /**
         * 获取全部生活指数
         */
        fun getIndicesTypeList(): ArrayList<IndicesType> {
            return arrayListOf(IndicesType.ALL)
        }

        /**
         * 获取默认生活指数
         * <p>
         *运动指数；穿衣指数；紫外线指数；花粉过敏指数；舒适度指数；感冒指数
         */
        fun getDefaultIndicesTypeList(): ArrayList<IndicesType> {
            return arrayListOf(
                IndicesType.SPT,
                IndicesType.DRSG,
                IndicesType.AG,
                IndicesType.UV,
                IndicesType.COMF,
                IndicesType.FLU
            )
        }

        /**
         * 获取生活指数logo
         */
        fun getIndicesLogo(type: String): Int {
            var logoId = -1
            when (type) {
                "1" -> {//运动指数
                    logoId = R.mipmap.icon_sports
                }
                "2" -> {//洗车指数
                    logoId = R.mipmap.icon_watching_car
                }
                "3" -> {//穿衣指数
                    logoId = R.mipmap.icon_clothes
                }
                "4" -> {//钓鱼指数
                    logoId = R.mipmap.icon_finishing
                }
                "5" -> {//紫外线指数
                    logoId = R.mipmap.icon_uv
                }
                "6" -> {//旅游指数
                    logoId = R.mipmap.icon_travel
                }
                "7" -> {//花粉过敏指数
                    logoId = R.mipmap.icon_allergy
                }
                "8" -> {//舒适度指数
                    logoId = R.mipmap.icon_comfort
                }
                "9" -> {//感冒指数
                    logoId = R.mipmap.icon_cold
                }
                "10" -> {//空气污染扩散条件指
                    logoId = R.mipmap.icon_pollution
                }
                "11" -> {//空调开启指数
                    logoId = R.mipmap.icon_air_conditioning
                }
                "12" -> {//太阳镜指数
                    logoId = R.mipmap.icon_glasses
                }
                "13" -> {//化妆指数
                    logoId = R.mipmap.icon_makeup
                }
                "14" -> {//晾晒指数
                    logoId = R.mipmap.icon_dry
                }
                "15" -> {//交通指数
                    logoId = R.mipmap.icon_traffic
                }
                "16" -> {//防晒指数
                    logoId = R.mipmap.icon_sun_protection
                }
                else -> {
                    logoId = -1
                }
            }
            return logoId
        }

    }
}