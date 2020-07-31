package com.coding.zxm.network

import com.coding.zxm.network.interceptor.WanInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 */
class RetrofitClient private constructor() {

    companion object {
        val INSTANCE = RetrofitClientHolder.client
    }

    private object RetrofitClientHolder {
        val client = RetrofitClient()
    }

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(IApiService.BASE_URL)
            .client(initOkhttpClient())
            .build()
    }

    private fun initOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(WanInterceptor())
            .addInterceptor(initLogInterceptor())
            .connectTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(30000, TimeUnit.SECONDS)
            .writeTimeout(30000, TimeUnit.SECONDS)
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

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}