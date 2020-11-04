package com.coding.zxm.weather.listener

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 * 天气结果回调
 */
interface OnWeatherResultListener {
    fun onError(throwable: Throwable?)

    fun onSuccess(result: String)
}