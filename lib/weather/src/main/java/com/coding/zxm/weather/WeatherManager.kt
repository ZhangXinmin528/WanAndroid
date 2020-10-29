package com.coding.zxm.weather

import android.content.Context
import com.alibaba.fastjson.JSON
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.HeConfig
import com.qweather.sdk.view.QWeather

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 */
class WeatherManager private constructor() {

    companion object {
        val INSTANCE: WeatherManager = Holder.holder
    }

    private object Holder {
        val holder = WeatherManager()
    }

    fun init() {
        //测试
//        HeConfig.init("HE1801192010091882", "006f98c203c944bc93195e70c3d72c8f")
        HeConfig.init("HE2010261719461230", "bd86df080cef4ac98128e443afea4306")
        HeConfig.switchToDevService()
    }

    /**
     * 获取实时天气数据
     */
    fun getWeatherNow(
        context: Context,
        location: String,
        onWeatherResultListener: OnWeatherResultListener
    ) {
        QWeather.getWeatherNow(context, location, object : QWeather.OnResultWeatherNowListener {
            override fun onSuccess(p0: WeatherNowBean?) {
                if (p0 == null) {
                    onWeatherResultListener.onError(NullPointerException("天气数据为空！"))
                } else {
                    onWeatherResultListener.onSuccess(JSON.toJSONString(p0))
                }
            }

            override fun onError(p0: Throwable?) {
                onWeatherResultListener.onError(p0)
            }

        })

    }

}