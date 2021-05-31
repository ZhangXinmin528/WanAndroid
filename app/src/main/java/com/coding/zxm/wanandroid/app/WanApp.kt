package com.coding.zxm.wanandroid.app

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.coding.zxm.core.base.BaseApp
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.upgrade.UpgradeManager
import com.coding.zxm.wanandroid.BuildConfig
import com.coding.zxm.wanandroid.ui.activity.SplashActivity
import com.coding.zxm.weather.WeatherManager
import com.coding.zxm.webview.TbsManager
import com.tencent.bugly.Bugly
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.zxm.utils.core.constant.TimeConstants
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.time.TimeUtil
import kotlin.math.abs

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/29 . All rights reserved.
 */
class WanApp : BaseApp() {

    //需要重新申请
    init {
        //微信申请不成功
//        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0")
        //Sina
//        PlatformConfig.setSinaWeibo(
//            "3039106221",
//            "0e17a8b656e1e17a7f3779579322c029",
//            "http://sns.whalecloud.com"
//        )
        //qq
        PlatformConfig.setQQZone("1110995000", "JLvvTN0zi5w25Rh2")
        PlatformConfig.setQQFileProvider("com.tencent.wanandroid.fileprovider")
        //dingding
        PlatformConfig.setDing("dingoafgactegaqwn14eih")
    }


    companion object {
        private const val TAG = "WanApp"
        private var mLastBackgroundTime: Long = 0

        fun getApplicationContext(): Context {
            return context
        }
    }


    override fun onCreate() {
        super.onCreate()

        addAppLifecycleObserver()

        initRetrofit()

        initTBS()

        initUMeng()

        initWeather()

        initLogger()

        initBugly()

        initUpgrade()
    }

    private fun initUpgrade() {
        val config = UpgradeManager.UpgradeConfig()
            .setContext(this)
            .setUpgradeToken("911a59ee1bfdd702ccdd1935bde1fe30")
            .setApkName("wanandroid")
            .create()

        UpgradeManager.getInstance().init(config)
    }

    private fun initTBS() {
        TbsManager.getInstance(this)?.initTBS()
    }

    private fun initBugly() {

        Bugly.init(this, "c61c145a8e", BuildConfig.DEBUG)

    }

    private fun initRetrofit() {
        RetrofitClient.getInstance(this)
        //添加其余BaseUrl
        RetrofitClient.putDoman()
        //是否开启调试模式
        RetrofitClient.setLogEnable(BuildConfig.DEBUG)
    }


    private fun initLogger() {

        MLogger.setLogEnable(this, BuildConfig.DEBUG)
    }

    private fun initWeather() {
        WeatherManager.INSTANCE.init(this)
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

    /**
     * App生命周期
     */
    private fun addAppLifecycleObserver() {

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    class AppLifecycleObserver : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun onCreate() {
            MLogger.d(TAG, "Application..onCreate()")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            MLogger.d(TAG, "Application..onStart()")
            val span = TimeUtil.getTimeSpanByNow(mLastBackgroundTime, TimeConstants.MIN)
            if (abs(span) > 5 && mLastBackgroundTime != 0L) {
                val intent = Intent(context, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun onResume() {
            MLogger.d(TAG, "Application..onResume()")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            MLogger.d(TAG, "Application..onStop()")
            mLastBackgroundTime = System.currentTimeMillis()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestory() {
            MLogger.d(TAG, "Application..onDestory()")
        }
    }

}