package com.coding.zxm.wanandroid.app

import android.content.Context
import com.coding.zxm.core.base.app.BaseApp

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/29 . All rights reserved.
 */
class WanApp : BaseApp() {

    companion object {

        fun getApplicationContext(): Context {
            return BaseApp.getApplicationContext()
        }
    }

    override fun onCreate() {
        super.onCreate()

    }
}