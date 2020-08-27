package com.coding.zxm.wanandroid.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.coding.zxm.network.RetrofitClient

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/29 . All rights reserved.
 */
class WanApp : MultiDexApplication() {

    companion object {
        lateinit var context: Application

        fun getApplicationContext(): Context {
            return context
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        RetrofitClient.getInstance(this)
    }
}