package com.coding.zxm.weather.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.weather.R
import com.coding.zxm.weather.ScrollWatched
import com.coding.zxm.weather.ScrollWatcher
import com.coding.zxm.weather.adapter.Weather7DayAdapter
import com.coding.zxm.weather.util.IconUtils
import com.coding.zxm.weather.util.WeatherUtil
import com.qweather.sdk.bean.IndicesBean
import com.qweather.sdk.bean.air.AirNowBean
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import com.zxm.utils.core.bar.StatusBarCompat
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
 * 传入经度（Longitude）和纬度（Latitude）获取天气信息
 */
class WeatherFragment : BaseFragment(), ScrollWatched {

    companion object {
        private val DEFAULT_FORMAT: DateFormat =
            SimpleDateFormat("HH:mm", Locale.CHINA)

        private const val PARAMS_LON = "params_longitude"
        private const val PARAMS_LAT = "params_latitude"
        private const val PARAMS_LOCATION_NAME = "params_location_name"

        fun newInstance(lon: Double, lat: Double, locationName: String): WeatherFragment {
            val fragment = WeatherFragment()
            var args = Bundle()
            args.putDouble(PARAMS_LON, lon)
            args.putDouble(PARAMS_LAT, lat)
            args.putString(PARAMS_LOCATION_NAME, locationName)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var mLocationName: String

    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0

    override fun setLayoutId(): Int {
        return R.layout.fragment_weather
    }

    private val mWatcherList: MutableList<ScrollWatcher> = ArrayList()

    override fun initParamsAndValues() {
        activity?.let {
            StatusBarCompat.setTranslucentForImageViewInFragment(activity, iv_weather_back)
        }

        mLocationName = arguments?.getString(PARAMS_LOCATION_NAME, "") as String

        mLatitude = arguments?.getDouble(PARAMS_LAT, 0.0) as Double
        mLongitude = arguments?.getDouble(PARAMS_LON, 0.0) as Double

        tv_weather_location.text = mLocationName
    }

    override fun initViews(rootView: View) {

        getWeatherNow()

        getWeather7D()
//
//        getWeather24Hourly()
//
        getAirNow()

        getIndices1D()

    }

    /**
     * 请求实况天气
     */
    private fun getWeatherNow() {
        QWeather.getWeatherNow(
            mContext,
            "$mLongitude,$mLatitude",
            object : QWeather.OnResultWeatherNowListener {
                @SuppressLint("SetTextI18n")
                override fun onSuccess(p0: WeatherNowBean?) {

                    MLogger.d(TAG, "getWeatherNow${JSON.toJSONString(p0)}")

                    if (p0 != null && p0.code == "200") {

                        tv_weather_temp.text = "${p0.now.temp}°"

                        tv_weather_text.text = p0.now.text

                        val iconId = IconUtils.getWeatherIcon(p0.now.icon)
                        if (iconId != -1) {
                            iv_weather_now_icon.setImageResource(iconId)
                        }

                        tv_weather_humidity.text = "湿度 ${p0.now.humidity}%"

                        tv_weather_feellike_temp.text = "体感温度 ${p0.now.feelsLike}°"

                        tv_weather_pressure.text = "气压 ${p0.now.pressure} 百帕"

                        tv_weather_time.text = "${TimeUtil.getNowString(DEFAULT_FORMAT)} 更新"

                        if (WeatherUtil.isInDayOrNight()) {
                            iv_weather_back.setImageResource(IconUtils.getDayBack(p0.now.icon))
                        } else {
                            iv_weather_back.setImageResource(IconUtils.getNightBack(p0.now.icon))
                        }

                        //风力风向
                        tv_wind_dir.text = "风向：${p0.now.windDir}"
                        tv_wind_scale.text = "风力： ${p0.now.windScale}级"
                        tv_wind_speed.text = "风速： ${p0.now.windSpeed}公里/小时"
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
            "$mLongitude,$mLatitude",
            object : QWeather.OnResultWeatherDailyListener {

                override fun onError(p0: Throwable?) {
                }

                override fun onSuccess(p0: WeatherDailyBean?) {
//                    MLogger.d(TAG, "getWeather7D${JSON.toJSONString(p0)}")
                    p0?.let {
                        if (it.code == "200") {
                            rv_weather_7d.adapter =
                                Weather7DayAdapter(
                                    it.daily
                                )
                            rv_weather_7d.layoutManager = LinearLayoutManager(mContext)
                            val itemDecoration = DividerItemDecoration(
                                mContext,
                                DividerItemDecoration.VERTICAL
                            )
                            ContextCompat.getDrawable(
                                context!!,
                                R.mipmap.icon_search_divider
                            )
                                ?.let {
                                    itemDecoration.setDrawable(
                                        it
                                    )
                                }
                            rv_weather_7d.addItemDecoration(itemDecoration)
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
            "$mLongitude,$mLatitude",
            null,
            object : QWeather.OnResultAirNowListener {
                @SuppressLint("SetTextI18n")
                override fun onSuccess(p0: AirNowBean?) {

//                    MLogger.d(TAG, "getAirNow${JSON.toJSONString(p0)}")

                    if (p0 != null && p0.code == "200") {

                        val nowBean = p0.now

                        tv_aqi_value.text = "AQI ${nowBean.category}"
                        when (nowBean.level) {
                            "1" -> {
                                tv_aqi_value.setBackgroundResource(R.drawable.shape_aqi_excellent)
                            }
                            "2" -> {
                                tv_aqi_value.setBackgroundResource(R.drawable.shape_aqi_good)
                            }
                            "3" -> {
                                tv_aqi_value.setBackgroundResource(R.drawable.shape_aqi_low)
                            }
                            "4" -> {
                                tv_aqi_value.setBackgroundResource(R.drawable.shape_aqi_mid)
                            }
                            "5" -> {
                                tv_aqi_value.setBackgroundResource(R.drawable.shape_aqi_bad)
                            }
                            "6" -> {
                                tv_aqi_value.setBackgroundResource(R.drawable.shape_aqi_serious)
                            }
                        }

                        aqiview.setAQIData(p0)

                        val layoutInflater = LayoutInflater.from(mContext)

                        //空气质量指标
                        //PM2.5
                        val pm2_5 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        pm2_5.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.pm2p5
                        pm2_5.findViewById<TextView>(R.id.tv_air_now_type).text = "PM2.5"
                        pm2_5.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIDrawableRes(nowBean.pm2p5))

                        layout_air_container.addView(pm2_5)

                        //PM10
                        val pm10 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        pm10.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.pm10
                        pm10.findViewById<TextView>(R.id.tv_air_now_type).text = "PM10"
                        pm10.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIDrawableRes(nowBean.pm10))

                        layout_air_container.addView(pm10)

                        //O3
                        val o3 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        o3.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.o3
                        o3.findViewById<TextView>(R.id.tv_air_now_type).text = "O3"
                        o3.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIDrawableRes(nowBean.o3))

                        layout_air_container.addView(o3)

                        //CO
                        val co = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        co.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.co
                        co.findViewById<TextView>(R.id.tv_air_now_type).text = "CO"
                        co.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIDrawableRes(nowBean.co))

                        layout_air_container.addView(co)

                        //so2
                        val so2 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        so2.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.so2
                        so2.findViewById<TextView>(R.id.tv_air_now_type).text = "SO2"
                        so2.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIDrawableRes(nowBean.so2))

                        layout_air_container.addView(so2)

                        //no2
                        val no2 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        no2.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.no2
                        no2.findViewById<TextView>(R.id.tv_air_now_type).text = "No2"
                        no2.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIDrawableRes(nowBean.no2))

                        layout_air_container.addView(no2)
                    }
                }

                override fun onError(p0: Throwable?) {

                }


            })
    }

    /**
     * 获取今天生活指数
     */
    private fun getIndices1D() {
        QWeather.getIndices1D(
            mContext,
            "$mLongitude,$mLatitude",
            Lang.ZH_HANS,
            WeatherUtil.getDefaultIndicesTypeList(),
            object : QWeather.OnResultIndicesListener {

                override fun onError(p0: Throwable?) {
                }

                override fun onSuccess(p0: IndicesBean?) {
//                    MLogger.d(TAG, "getIndices1D${JSON.toJSONString(p0)}")
                    p0?.let {
                        if (it.code == "200") {
                            val layoutInflater = LayoutInflater.from(mContext)
                            it.dailyList.forEach { item ->

                                //空气质量指标
                                //PM2.5
                                val layout =
                                    layoutInflater.inflate(R.layout.layout_indices_item, null)
                                layout.findViewById<TextView>(R.id.tv_indices_category).text =
                                    item.category
                                layout.findViewById<TextView>(R.id.tv_indices_name).text = item.name

                                layout.findViewById<ImageView>(R.id.iv_indices_logo)
                                    .setImageResource(WeatherUtil.getIndicesLogo(item.type))

                                layout_indices_container.addView(layout)

                                if (!TextUtils.isEmpty(item.text)) {
                                    layout.setOnClickListener {
                                        Toast.makeText(mContext, item.text, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }
                        }
                    }
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