package com.coding.zxm.weather

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
        HeConfig.init("HE2010261719461230", "bd86df080cef4ac98128e443afea4306")
    }

    fun getWeatherNow(){
    }

}