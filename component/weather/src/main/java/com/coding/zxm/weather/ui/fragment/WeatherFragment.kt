package com.coding.zxm.weather.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.weather.R
import com.coding.zxm.weather.adapter.Weather7DayAdapter
import com.coding.zxm.weather.databinding.FragmentWeatherBinding
import com.coding.zxm.weather.util.IconUtils
import com.coding.zxm.weather.util.WeatherUtil
import com.qweather.sdk.bean.IndicesBean
import com.qweather.sdk.bean.air.AirNowBean
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Unit
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import com.zxm.utils.core.bar.StatusBarCompat
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.time.TimeUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 * 传入经度（Longitude）和纬度（Latitude）获取天气信息
 * TODO:需要适配多语言
 */
class WeatherFragment : BaseFragment() {

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


    private lateinit var weatherBinding: FragmentWeatherBinding

    private lateinit var mLocationName: String

    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0

    private var mBackBitmap: Bitmap? = null

    override fun setContentLayout(container: ViewGroup?): View {
        weatherBinding = FragmentWeatherBinding.inflate(layoutInflater,container,false)
        return weatherBinding.root
    }

    override fun initParamsAndValues() {

        mLocationName = arguments?.getString(PARAMS_LOCATION_NAME, "") as String

        mLatitude = arguments?.getDouble(PARAMS_LAT, 0.0) as Double
        mLongitude = arguments?.getDouble(PARAMS_LON, 0.0) as Double

        weatherBinding.tvWeatherLocation.text = mLocationName
    }

    override fun initViews() {

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
            mContext?.let { WeatherUtil.getQWeatherLanCode(it) },
            Unit.METRIC,
            object : QWeather.OnResultWeatherNowListener {
                @SuppressLint("SetTextI18n")
                override fun onSuccess(p0: WeatherNowBean?) {

//                    MLogger.d(TAG, "getWeatherNow${JSON.toJSONString(p0)}")

                    if (p0 != null && p0.code == Code.OK) {

                        weatherBinding.tvWeatherTemp.text = "${p0.now.temp}°"

                        weatherBinding.tvWeatherText.text = p0.now.text

                        val iconId = IconUtils.getWeatherIcon(p0.now.icon)
                        if (iconId != -1) {
                            weatherBinding.ivWeatherNowIcon.setImageResource(iconId)
                        }

                        weatherBinding.tvWeatherHumidity.text = "湿度 ${p0.now.humidity}%"

                        weatherBinding.tvWeatherFeellikeTemp.text = "体感温度 ${p0.now.feelsLike}°"

                        weatherBinding.tvWeatherPressure.text = "气压 ${p0.now.pressure} 百帕"

                        weatherBinding.tvWeatherTime.text =
                            "${TimeUtil.getNowString(DEFAULT_FORMAT)} 更新"

                        if (WeatherUtil.isInDayOrNight()) {
                            mBackBitmap = BitmapFactory.decodeResource(
                                resources,
                                IconUtils.getDayBack(p0.now.icon)
                            )
                        } else {
                            mBackBitmap = BitmapFactory.decodeResource(
                                resources,
                                IconUtils.getNightBack(p0.now.icon)
                            )
                        }

                        mBackBitmap?.let {
                            weatherBinding.ivWeatherBack.setImageBitmap(it)
                            Palette.from(it).generate { palette ->
                                palette.let {
                                    val muteSwatch = palette?.mutedSwatch
                                    val swatch =
                                        if (palette?.vibrantSwatch == null) muteSwatch else palette?.vibrantSwatch

                                    if (swatch != null) {
                                        val bgColor = swatch.rgb
                                        weatherBinding.layoutWeatherContent.setBackgroundColor(
                                            bgColor
                                        )
                                        StatusBarCompat.setColorNoTranslucent(activity, bgColor)
                                    }
                                }
                            }
                        }

                        //风力风向
                        weatherBinding.tvWindDir.text = "风向：${p0.now.windDir}"
                        weatherBinding.tvWindScale.text = "风力： ${p0.now.windScale}级"
                        weatherBinding.tvWindSpeed.text = "风速： ${p0.now.windSpeed}公里/小时"
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
            "$mLongitude,$mLatitude",
            object : QWeather.OnResultWeatherHourlyListener {

                override fun onError(p0: Throwable?) {

                }

                override fun onSuccess(p0: WeatherHourlyBean?) {
//                    MLogger.d(TAG, "getWeather24Hourly${JSON.toJSONString(p0)}")

                    p0?.let {
                        if (it.code == Code.OK) {
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
            mContext?.let { WeatherUtil.getQWeatherLanCode(it) },
            Unit.METRIC,
            object : QWeather.OnResultWeatherDailyListener {

                override fun onError(p0: Throwable?) {
                }

                override fun onSuccess(p0: WeatherDailyBean?) {
                    MLogger.d(TAG, "getWeather7D${JSON.toJSONString(p0)}")
                    p0?.let {
                        if (it.code == Code.OK) {
                            weatherBinding.rvWeather7d?.adapter =
                                Weather7DayAdapter(
                                    it.daily
                                )
                            weatherBinding.rvWeather7d?.layoutManager =
                                LinearLayoutManager(mContext)
//                            val itemDecoration = DividerItemDecoration(
//                                mContext,
//                                DividerItemDecoration.VERTICAL
//                            )
//                            ContextCompat.getDrawable(
//                                context!!,
//                                R.mipmap.icon_search_divider
//                            )
//                                ?.let {
//                                    itemDecoration.setDrawable(
//                                        it
//                                    )
//                                }
//                            rv_weather_7d?.addItemDecoration(itemDecoration)
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
            mContext?.let { WeatherUtil.getQWeatherLanCode(it) },
            object : QWeather.OnResultAirNowListener {
                @SuppressLint("SetTextI18n")
                override fun onSuccess(p0: AirNowBean?) {

                    MLogger.d(TAG, "getAirNow${JSON.toJSONString(p0)}")

                    if (p0 != null && p0.code == Code.OK) {

                        val nowBean = p0.now

                        weatherBinding.tvAqiValue.text = "AQI ${nowBean.category}"
                        when (nowBean.level) {
                            "1" -> {
                                weatherBinding.tvAqiValue.setBackgroundResource(R.drawable.shape_aqi_excellent)
                            }
                            "2" -> {
                                weatherBinding.tvAqiValue.setBackgroundResource(R.drawable.shape_aqi_good)
                            }
                            "3" -> {
                                weatherBinding.tvAqiValue.setBackgroundResource(R.drawable.shape_aqi_low)
                            }
                            "4" -> {
                                weatherBinding.tvAqiValue.setBackgroundResource(R.drawable.shape_aqi_mid)
                            }
                            "5" -> {
                                weatherBinding.tvAqiValue.setBackgroundResource(R.drawable.shape_aqi_bad)
                            }
                            "6" -> {
                                weatherBinding.tvAqiValue.setBackgroundResource(R.drawable.shape_aqi_serious)
                            }
                        }

                        weatherBinding.aqiview.setAQIData(p0)

                        val layoutInflater = LayoutInflater.from(mContext)

                        //空气质量指标
                        //PM2.5
                        val pm2_5 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        pm2_5.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.pm2p5
                        pm2_5.findViewById<TextView>(R.id.tv_air_now_type).text = "PM2.5"
                        pm2_5.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIPM2_5DrawableRes(nowBean.pm2p5))

                        weatherBinding.layoutAirContainer.addView(pm2_5)

                        //PM10
                        val pm10 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        pm10.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.pm10
                        pm10.findViewById<TextView>(R.id.tv_air_now_type).text = "PM10"
                        pm10.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIPM10DrawableRes(nowBean.pm10))

                        weatherBinding.layoutAirContainer.addView(pm10)

                        //O3
                        val o3 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        o3.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.o3
                        o3.findViewById<TextView>(R.id.tv_air_now_type).text = "O3"
                        o3.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQIO3DrawableRes(nowBean.o3))

                        weatherBinding.layoutAirContainer.addView(o3)

                        //CO
                        val co = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        co.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.co
                        co.findViewById<TextView>(R.id.tv_air_now_type).text = "CO"
                        co.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQICODrawableRes(nowBean.co))

                        weatherBinding.layoutAirContainer.addView(co)

                        //so2
                        val so2 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        so2.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.so2
                        so2.findViewById<TextView>(R.id.tv_air_now_type).text = "SO2"
                        so2.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQISO2DrawableRes(nowBean.so2))

                        weatherBinding.layoutAirContainer.addView(so2)

                        //no2
                        val no2 = layoutInflater.inflate(R.layout.layout_air_now_item, null)
                        no2.findViewById<TextView>(R.id.tv_air_now_value).text = nowBean.no2
                        no2.findViewById<TextView>(R.id.tv_air_now_type).text = "NO2"
                        no2.findViewById<TextView>(R.id.tv_aqi_indicator)
                            .setBackgroundResource(WeatherUtil.getAQINO2DrawableRes(nowBean.no2))

                        weatherBinding.layoutAirContainer.addView(no2)
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
            mContext?.let { WeatherUtil.getQWeatherLanCode(it) },
            WeatherUtil.getDefaultIndicesTypeList(),
            object : QWeather.OnResultIndicesListener {

                override fun onError(p0: Throwable?) {
                }

                override fun onSuccess(p0: IndicesBean?) {
//                    MLogger.d(TAG, "getIndices1D${JSON.toJSONString(p0)}")
                    p0?.let {
                        if (it.code == Code.OK) {
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

                                weatherBinding.layoutIndicesContainer.addView(layout)

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

    override fun onDestroy() {
        mBackBitmap?.let {
            if (!it.isRecycled) {
                it.recycle()
            }
        }
        super.onDestroy()
    }
}