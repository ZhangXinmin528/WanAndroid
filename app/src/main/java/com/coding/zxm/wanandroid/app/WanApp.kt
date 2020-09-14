package com.coding.zxm.wanandroid.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.BuildConfig
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

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

        initUMeng()

    }


    private fun initUMeng() {
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)

        //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
        UMConfigure.init(
            this,
            "5f55deda3739314483bca2df",
            "Umeng",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )

        //统计SDK是否支持采集在子进程中打点的自定义事件，默认不支持
        UMConfigure.setProcessEvent(true) //支持多进程打点


        //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
    }

    //需要重新申请
    init {
        //申请不成功
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0")
        //Sina
        PlatformConfig.setSinaWeibo(
            "3039106221",
            "0e17a8b656e1e17a7f3779579322c029",
            "http://sns.whalecloud.com"
        )
        //qq
        PlatformConfig.setQQZone("1110995000", "JLvvTN0zi5w25Rh2")
        PlatformConfig.setQQFileProvider("com.tencent.wanandroid.fileprovider")
        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
    }
}