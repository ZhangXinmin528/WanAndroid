package com.coding.zxm.weather.entity

import com.qweather.sdk.bean.Basic
import com.qweather.sdk.bean.Refer

/**
 * 天气实况数据类
 * <p>
 *     详细文档，请参阅：
 *     https://dev.qweather.com/docs/api/weather/
 */
class WeatherNowEntity {
    var code: String? = null

    //实况数据
    var now: NowBaseBean? = null
    var basic: Basic? = null
    var refer: Refer? = null

    class NowBaseBean {
        var obsTime: String? = null
        var temp: String? = null
        var feelsLike: String? = null
        var icon: String? = null
        var text: String? = null
        var wind360: String? = null
        var windDir: String? = null
        var windScale: String? = null
        var windSpeed: String? = null
        var humidity: String? = null
        var precip: String? = null
        var pressure: String? = null
        var vis: String? = null
        var cloud: String? = null
        var dew: String? = null

    }
}