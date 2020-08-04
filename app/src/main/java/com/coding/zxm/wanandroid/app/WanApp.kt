package com.coding.zxm.wanandroid.app

import android.app.Application
import android.content.Context

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/29 . All rights reserved.
 */
class WanApp : Application() {
    companion object {
        lateinit var context: Application

        fun getApplicationContext(): Context {
            return context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}