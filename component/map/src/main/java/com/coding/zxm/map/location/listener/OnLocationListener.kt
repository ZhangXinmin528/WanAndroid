package com.coding.zxm.map.location.listener

import com.amap.api.location.AMapLocation

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/6 . All rights reserved.
 * 定位接口
 */
interface OnLocationListener {
    /**
     * 定位成功
     */
    fun onLocationSuccess(location: AMapLocation)

    /**
     * 定位失败
     */
    fun onLocationFailure(errorCode: Int, errorMsg: String)
}