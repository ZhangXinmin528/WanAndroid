package com.coding.zxm.weather

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.zxm.utils.core.time.TimeUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/2 . All rights reserved.
 */
class Weather7DayAdapter(dataList: MutableList<WeatherDailyBean.DailyBean>) :
    BaseQuickAdapter<WeatherDailyBean.DailyBean, BaseViewHolder>(
        data = dataList, layoutResId = R.layout.layout_weather_7d_item
    ) {

    private val DEFAULT_FORMAT: DateFormat =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun convert(holder: BaseViewHolder, item: WeatherDailyBean.DailyBean) {

        holder.setImageResource(R.id.iv_day_icon_7d, IconUtils.getWeatherIcon(item.iconDay))
            .setImageResource(R.id.iv_night_icon_7d, IconUtils.getWeatherIcon(item.iconNight))
            .setText(R.id.tv_temp_range_7d, "${item.tempMax} / ${item.tempMin}°")

        if (holder.layoutPosition == 0) {
            holder.setText(R.id.tv_date_7d, "今天")
        } else {
            holder.setText(R.id.tv_date_7d, TimeUtil.getChineseWeek(item.fxDate, DEFAULT_FORMAT))
        }

    }
}