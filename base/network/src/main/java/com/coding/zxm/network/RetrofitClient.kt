package com.coding.zxm.network

import android.content.Context
import com.coding.zxm.chuck.Chuck
import com.coding.zxm.chuck.ChuckInterceptor
import com.coding.zxm.network.cookie.PersistentCookieJar
import com.coding.zxm.network.cookie.cache.SetCookieCache
import com.coding.zxm.network.cookie.persistence.SharedPrefsCookiePersistor
import com.coding.zxm.network.interceptor.NetworkInterceptor
import com.coding.zxm.network.interceptor.WanInterceptor
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 */
class RetrofitClient private constructor(private val context: Context) {

    companion object {
        private var INSTANCE: RetrofitClient? = null
        private var sLOGENABLE: Boolean = true

        @Synchronized
        fun getInstance(context: Context): RetrofitClient {
            if (INSTANCE == null) {
                INSTANCE = RetrofitClient(context)
            }
            return INSTANCE!!
        }

        /**
         * 添加其他BaseUrl
         */
        fun putDoman() {
            RetrofitUrlManager.getInstance()
                .putDomain(APIConstants.DOMAN_BING, APIConstants.BING_URL)
        }

        /**
         * 是否打印日志
         */
        fun setLogEnable(enable: Boolean) {
            sLOGENABLE = enable
        }

    }

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(APIConstants.BASE_URL)
            .client(initOkhttpClient())
            .build()

        RetrofitUrlManager.getInstance().setDebug(true)
    }

    private fun initOkhttpClient(): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "responses")
        val cacheSize = 5 * 1024 * 1024L // 5 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)

        val cookieJar = PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(context)
        )

        val chuckInterceptor = ChuckInterceptor(context)
        chuckInterceptor.showNotification(false)

        val builder = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(WanInterceptor())
            .addInterceptor(initLogInterceptor())
            .addInterceptor(NetworkInterceptor(context))
            .addInterceptor(chuckInterceptor)
            .cookieJar(cookieJar)
            .connectTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(30000, TimeUnit.SECONDS)
            .writeTimeout(30000, TimeUnit.SECONDS)

        return RetrofitUrlManager.getInstance()
            .with(builder)
            .build()
    }

    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (sLOGENABLE) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    /**
     * 查阅调试信息
     */
    fun toChuck(context: Context) {
        val intent = Chuck.getLaunchIntent(context)
        context.startActivity(intent)
    }
}