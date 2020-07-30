package com.coding.zxm.network

import com.coding.zxm.network.interceptor.WanInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 */
class RetrofitClient private constructor() {

    companion object {

    }

    private fun initOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(WanInterceptor())
            .addInterceptor(initLogInterceptor())
            .build()
    }

    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (true) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }
}