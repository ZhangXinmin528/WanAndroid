package com.coding.zxm.map

import android.content.Context
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.coding.zxm.map.location.listener.OnLocationListener
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/5 . All rights reserved.
 * 定位管理器
 * <p>
 *     详细文档
 *     请阅读：https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation
 */
class LocationManager private constructor() : AMapLocationListener {

    private val mDateFormat =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    companion object {
        private const val TAG = "LocationManager"
        val INSTANCE: LocationManager = Holder.holder
    }

    private object Holder {
        val holder = LocationManager()
    }

    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var mLocationListener: OnLocationListener? = null

    /**
     * Use application context
     */
    fun initClient(context: Context): LocationManager {
        if (mLocationClient == null) {
            mLocationClient = AMapLocationClient(context)

        }
        mLocationClient?.setLocationListener(this)
        return this
    }

    /**
     * Update location option
     * <p>
     *     only location once
     */
    fun setOnceLocationOption(): LocationManager {
        if (mLocationOption != null) {
            mLocationClient = null
        }
        mLocationOption = AMapLocationClientOption()
        mLocationOption?.isNeedAddress = true
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption?.isOnceLocation = true
        mLocationClient?.setLocationOption(mLocationOption)
        return this
    }

    /**
     * Start location
     */
    fun startLocation(onLocationListener: OnLocationListener) {
        mLocationClient?.let {
            if (!it.isStarted) {
                it.startLocation()
                mLocationListener = onLocationListener
            }
        }

    }

    /**
     * Stop location
     */
    fun stopLocation() {
        if (mLocationClient != null) {
            if (mLocationClient!!.isStarted) {
                mLocationClient!!.stopLocation()
            }
        }
    }


    fun OnDestory() {
        mLocationClient?.let {
            it.onDestroy()
            mLocationClient = null
            mLocationOption = null
        }
    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {

        if (aMapLocation != null) {
            if (aMapLocation.errorCode == 0) {//定位成功
                if (mLocationListener != null) {
                    mLocationListener?.onLocationSuccess(aMapLocation)
                }
                Log.d(TAG, "定位成功：[Info]=${aMapLocation.toJson(1).toString()}")
            } else {
                if (mLocationListener != null) {
                    mLocationListener?.onLicationFailure(
                        aMapLocation.errorCode,
                        aMapLocation.errorInfo
                    )
                }
                Log.e(
                    TAG,
                    "定位失败：[ErrorCode]=${aMapLocation.errorCode}..[ErrorMsg]=${aMapLocation.errorInfo}"
                )
            }
        }
    }

    private fun formatTime(time: Long): String {
        return mDateFormat.format(Date(time))
    }


}