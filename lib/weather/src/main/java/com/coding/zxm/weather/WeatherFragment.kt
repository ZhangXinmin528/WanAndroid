package com.coding.zxm.weather

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.coding.zxm.core.base.BaseFragment
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import kotlinx.android.synthetic.main.fragment_weather.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 */
class WeatherFragment : BaseFragment() {

    companion object {
        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_weather
    }

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {
        srl_weather.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED)

        srl_weather.setOnRefreshListener {
            getWeatherNow()
        }

        getWeatherNow()
    }

    private fun getWeatherNow() {
        QWeather.getWeatherNow(mContext, "101010100", object : QWeather.OnResultWeatherNowListener {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(p0: WeatherNowBean?) {
                srl_weather.isRefreshing = false
                if (p0 != null && p0.code == "200") {

                    tv_weather_temp.text = "${p0.now.temp}°"
                    tv_weather_text.text = p0.now.text
                    tv_weather_humidity.text = " | 湿度 ${p0.now.humidity}%"
                    tv_weather_wind.text = "${p0.now.windDir} ${p0.now.windSpeed}级"

                }
            }

            override fun onError(p0: Throwable?) {
                srl_weather.isRefreshing = false
            }

        })
    }


}