package com.coding.zxm.network.interceptor

import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 */
class WanInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Content_Type", "application/json")
            .addHeader("charset", "UTF-8").build()
        return chain.proceed(request)
    }
}