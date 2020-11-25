package com.coding.zxm.wanandroid.weather

import android.util.Log
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.map.LocationManager
import com.coding.zxm.map.location.listener.OnLocationListener
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.weather.ui.fragment.WeatherFragment
import com.zxm.utils.core.log.MLogger

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/30 . All rights reserved.
 */
class WeatherActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_weather
    }

    override fun initParamsAndValues() {
        LocationManager.INSTANCE.initClient(WanApp.getApplicationContext())
            .setOnceLocationOption()
            .startLocation(object : OnLocationListener {

                override fun onLocationSuccess(location: AMapLocation) {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.container_weather, WeatherFragment.newInstance(
                                lon = location.longitude,
                                lat = location.latitude,
                                locationName = "${location.city} ${location.district}"
                            )
                        )
                        .commitAllowingStateLoss()

                }

                override fun onLicationFailure(errorCode: Int, errorMsg: String) {
                    Toast.makeText(mContext, "定位失败：$errorMsg", Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun initViews() {

    }

}