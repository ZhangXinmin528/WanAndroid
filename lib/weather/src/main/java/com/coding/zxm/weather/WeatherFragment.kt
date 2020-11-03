package com.coding.zxm.weather

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseFragment
import com.qweather.sdk.bean.air.AirNowBean
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.time.TimeUtil
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 */
class WeatherFragment : BaseFragment(), ScrollWatched {

    companion object {
        private val DEFAULT_FORMAT: DateFormat =
            SimpleDateFormat("HH:mm", Locale.getDefault())

        fun newInstance(): WeatherFragment {
            return WeatherFragment()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_weather
    }

    private val mWatcherList: MutableList<ScrollWatcher> = ArrayList()

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {

        getWeatherNow()

        getWeather7D()
//
//        getWeather24Hourly()
//
//        getAirNow()
    }

    /**
     * 请求实况天气
     */
    private fun getWeatherNow() {
        QWeather.getWeatherNow(mContext, "101010100", object : QWeather.OnResultWeatherNowListener {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(p0: WeatherNowBean?) {

//                MLogger.d(TAG, "getWeatherNow${JSON.toJSONString(p0)}")

                if (p0 != null && p0.code == "200") {

                    tv_weather_temp.text = "${p0.now.temp}°"

                    tv_weather_text.text = p0.now.text

                    val iconId = IconUtils.getWeatherIcon(p0.now.icon)
                    if (iconId != -1) {
                        iv_weather_now_icon.setImageResource(iconId)
                    }

                    tv_weather_humidity.text = "湿度 ${p0.now.humidity}%"

                    tv_weather_wind.text = "${p0.now.windDir} ${p0.now.windSpeed}级"

                    tv_weather_pressure.text = "气压 ${p0.now.pressure} 百帕"

                    tv_weather_time.text = "${TimeUtil.getNowString(DEFAULT_FORMAT)} 更新"

                    val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

                    if (hourOfDay in 6..17) {
                        iv_weather_back.setImageResource(IconUtils.getDayBack(p0.now.icon))
                    } else {
                        iv_weather_back.setImageResource(IconUtils.getNightBack(p0.now.icon))
                    }
                }
            }

            override fun onError(p0: Throwable?) {
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

                }

                override fun onSuccess(p0: WeatherHourlyBean?) {
//                    MLogger.d(TAG, "getWeather24Hourly${JSON.toJSONString(p0)}")

                    p0?.let {
                        if (it.code == "200") {
                            dealWithHourlyData(it)
                        }
                    }
                }

            })
    }

    @SuppressLint("SetTextI18n")
    private fun dealWithHourlyData(bean: WeatherHourlyBean) {
        if (bean.hourly != null) {
            val hourlyWeatherList: List<WeatherHourlyBean.HourlyBean> =
                bean.hourly
            val data: MutableList<WeatherHourlyBean.HourlyBean> =
                ArrayList()
            if (hourlyWeatherList.size > 23) {
                for (i in 0..23) {
                    data.add(hourlyWeatherList[i])
                    val condCode = data[i].icon
                    var time = data[i].fxTime
                    time = time.substring(time.length - 11, time.length - 9)
                    val hourNow = time.toInt()
                    if (hourNow in 6..19) {
                        data[i].icon = condCode + "d"
                    } else {
                        data[i].icon = condCode + "n"
                    }
                }
            } else {
                for (i in hourlyWeatherList.indices) {
                    data.add(hourlyWeatherList[i])
                    val condCode = data[i].icon
                    var time = data[i].fxTime
                    time = time.substring(time.length - 11, time.length - 9)
                    val hourNow = time.toInt()
                    if (hourNow in 6..19) {
                        data[i].icon = condCode + "d"
                    } else {
                        data[i].icon = condCode + "n"
                    }
                }
            }
            var minTmp = data[0].temp.toInt()
            var maxTmp = minTmp
            for (i in data.indices) {
                val tmp = data[i].temp.toInt()
                minTmp = Math.min(tmp, minTmp)
                maxTmp = Math.max(tmp, maxTmp)
            }
            //设置当天的最高最低温度
//            hourly_view.setHighestTemp(maxTmp)
//            hourly_view.setLowestTemp(minTmp)
//            if (maxTmp == minTmp) {
//                hourly_view.setLowestTemp(minTmp - 1)
//            }
//            hourly_view.initData(data)
//            tv_24h_high_temp.text = "$maxTmp°"
//            tv_24h_low_temp.text = "$minTmp°"

        }
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
                }

                override fun onSuccess(p0: WeatherDailyBean?) {
                    MLogger.d(TAG, "getWeather7D${JSON.toJSONString(p0)}")
                    p0?.let {
                        if (it.code == "200") {
                            rv_weather_7d.adapter = Weather7DayAdapter(it.daily)
                            rv_weather_7d.layoutManager = LinearLayoutManager(mContext)

                        }
                    }
                }

            })
    }

    /**
     * 获取今天的空气质量实况
     */
    private fun getAirNow() {
        QWeather.getAirNow(
            mContext,
            "101010100",
            null,
            object : QWeather.OnResultAirNowListener {
                override fun onSuccess(p0: AirNowBean?) {

                    MLogger.d(TAG, "getAirNow${JSON.toJSONString(p0)}")
                }

                override fun onError(p0: Throwable?) {
                }


            })
    }

    override fun notifyWatcher(x: Int) {
        mWatcherList.forEach {
            it.update(x)
        }
    }

    override fun addWatcher(watcher: ScrollWatcher?) {
        watcher?.let { mWatcherList.add(it) }
    }

    override fun removeWatcher(watcher: ScrollWatcher?) {
        watcher?.let {
            mWatcherList.remove(watcher)
        }
    }


}