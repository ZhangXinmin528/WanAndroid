package com.coding.zxm.weather

import android.view.View
import com.coding.zxm.core.base.BaseFragment
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import com.zxm.utils.core.log.MLogger

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 */
class WeatherFragment : BaseFragment() {

    override fun setLayoutId(): Int {
        return R.layout.fragment_weather
    }

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {
        getWeatherNow()
    }

    private fun getWeatherNow() {
        QWeather.getWeatherNow(mContext, "Beijing", object : QWeather.OnResultWeatherNowListener {
            override fun onSuccess(p0: WeatherNowBean?) {
                MLogger.d(TAG, "getWeatherNow()..${p0?.code}")
            }

            override fun onError(p0: Throwable?) {
            }

        })
    }



}