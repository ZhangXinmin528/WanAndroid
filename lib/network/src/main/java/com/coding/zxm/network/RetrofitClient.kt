package com.coding.zxm.network

import android.content.Context
import com.coding.zxm.network.cookie.PersistentCookieJar
import com.coding.zxm.network.cookie.cache.SetCookieCache
import com.coding.zxm.network.cookie.persistence.SharedPrefsCookiePersistor
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
class RetrofitClient private constructor(private val context: Context) {

    companion object {
        private var INSTANCE: RetrofitClient? = null

        @Synchronized
        fun getInstance(context: Context): RetrofitClient? {
            if (INSTANCE == null) {
                INSTANCE = RetrofitClient(context)
            }
            return INSTANCE
        }
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
        val cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(context)
        )

        return OkHttpClient.Builder()
            .addInterceptor(WanInterceptor())
            .addInterceptor(initLogInterceptor())
            .cookieJar(cookieJar)
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