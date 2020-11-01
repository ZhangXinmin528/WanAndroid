package com.coding.zxm.weather

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseFragment
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import com.zxm.utils.core.log.MLogger
import kotlinx.android.synthetic.main.fragment_weather.*
import org.joda.time.DateTime

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



//        getWeather3D()

        getWeather7D()

        getWeather24Hourly()
    }

    /**
     * 请求实况天气
     */
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

                    val nowTime = DateTime.now()
                    val hour = nowTime.hourOfDay
                    if (hour in 7..18) {
                        iv_weather_back.setImageResource(IconUtils.getDayBack(p0.now.icon))
                    } else {
                        iv_weather_back.setImageResource(IconUtils.getNightBack(p0.now.icon))
                    }
                }
            }

            override fun onError(p0: Throwable?) {
                srl_weather.isRefreshing = false
            }

        })
    }


    /**
     * 未来24小时天气预报
     */
    private fun getWeather24Hourly() {
        QWeather.getWeather24Hourly(
            mContext,
            "101010100",
            object : QWeather.OnResultWeatherHourlyListener {

                override fun onError(p0: Throwable?) {
                    srl_weather.isRefreshing = false
                }

                override fun onSuccess(p0: WeatherHourlyBean?) {
                    MLogger.d(TAG, "getWeather24Hourly${JSON.toJSONString(p0)}")
                }

            })
    }

    /**
     * 未来3天的天气
     */
    private fun getWeather3D() {
        QWeather.getWeather3D(
            mContext,
            "101010100",
            object : QWeather.OnResultWeatherDailyListener {

                override fun onError(p0: Throwable?) {
                    srl_weather.isRefreshing = false
                }

                override fun onSuccess(p0: WeatherDailyBean?) {
                    MLogger.d(TAG, "getWeather3D${JSON.toJSONString(p0)}")
                }

            })
    }

    /**
     * 未来7天的天气
     */
    private fun getWeather7D() {
        QWeather.getWeather7D(
            mContext,
            "101010100",
            object : QWeather.OnResultWeatherDailyListener {

                override fun onError(p0: Throwable?) {
                    srl_weather.isRefreshing = false
                }

                override fun onSuccess(p0: WeatherDailyBean?) {
                    MLogger.d(TAG, "getWeather7D${JSON.toJSONString(p0)}")
                }

            })
    }


}