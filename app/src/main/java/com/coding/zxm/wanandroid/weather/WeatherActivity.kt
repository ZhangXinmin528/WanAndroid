package com.coding.zxm.wanandroid.weather

import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.weather.ui.fragment.WeatherFragment

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/30 . All rights reserved.
 */
class WeatherActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun initParamsAndValues() {

    }

    override fun initViews() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_weather, WeatherFragment.newInstance())
            .commitAllowingStateLoss()
    }
}