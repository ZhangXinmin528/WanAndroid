package com.coding.zxm.weather

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/10/26 . All rights reserved.
 */
interface OnWeatherResultListener {
    fun onError(throwable: Throwable)

    fun onSuccess()

}