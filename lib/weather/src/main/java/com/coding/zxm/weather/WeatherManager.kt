package com.coding.zxm.weather

import android.content.Context
import com.alibaba.fastjson.JSON
import com.qweather.sdk.bean.base.Type
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.geo.GeoPoiBean
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
        HeConfig.init("HE2010261719461230", "bd86df080cef4ac98128e443afea4306")
        HeConfig.switchToDevService()
    }

    /**
     * 获取实时天气数据
     * @param location 需要查询地区的LocationID或以逗号分隔的经度/纬度坐标（十进制），LocationID可通过城市搜索服务获取。
     * 例如： location=101010100 或 location=116.41,39.92
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

    /**
     * 获取热门10个城市
     */
    fun getGeoTopCity(context: Context, onWeatherResultListener: OnWeatherResultListener) {

        QWeather.getGeoTopCity(context, object : QWeather.OnResultGeoListener {
            override fun onSuccess(p0: GeoBean?) {
                if (p0 == null) {
                    onWeatherResultListener.onError(NullPointerException("热门城市数据为空！"))
                } else {
                    onWeatherResultListener.onSuccess(JSON.toJSONString(p0))
                }
            }

            override fun onError(p0: Throwable?) {
                onWeatherResultListener.onError(p0)
            }

        })
    }

    /**
     * 城市信息搜索
     * @param location 输入需要查询的城市名称，可使用Location ID、多语言文字、以逗号分隔的经度/纬度坐标、
     * ADCode（仅限中国城市）。例如location=beijing， location=116.4,39.1
     */
    fun getGeoCityLookup(
        context: Context,
        location: String,
        onWeatherResultListener: OnWeatherResultListener
    ) {

        QWeather.getGeoCityLookup(context, location, object : QWeather.OnResultGeoListener {
            override fun onSuccess(p0: GeoBean?) {
                if (p0 == null) {
                    onWeatherResultListener.onError(NullPointerException("城市信息搜索数据为空！"))
                } else {
                    onWeatherResultListener.onSuccess(JSON.toJSONString(p0))
                }
            }

            override fun onError(p0: Throwable?) {
                onWeatherResultListener.onError(p0)
            }

        })
    }

    /**
     * POI信息搜索
     * @param location 输入需要查询的POI名称，可使用中文、英文。最少一个汉字或2个英文字母，
     * 返回结果将按照相关性和Rank值进行排列。例如location=故宫。
     *
     * <p>注意：目前只支持景点
     */

    fun getGeoPoiLookup(
        context: Context,
        location: String,
        onWeatherResultListener: OnWeatherResultListener
    ) {

        QWeather.getGeoPoiLookup(
            context,
            location,
            Type.SCENIC,
            object : QWeather.OnResultGeoPoiListener {

                override fun onSuccess(p0: GeoPoiBean?) {
                    if (p0 == null) {
                        onWeatherResultListener.onError(NullPointerException("POI信息搜索数据为空！"))
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