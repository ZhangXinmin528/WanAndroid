package com.coding.zxm.wanandroid.ui.activity

import android.os.CountDownTimer
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.MainActivity
import com.coding.zxm.wanandroid.R
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/28 . All rights reserved.
 */
class SplashActivity : BaseActivity() {

    private lateinit var mCountDownTimer: CountDownTimer

    override fun setLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initParamsAndValues() {

    }

    override fun initViews() {

        mCountDownTimer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onFinish() {
                jumpActivity(MainActivity::class.java)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                tv_splash_timer.text = String.format("%ss", time)
            }

        }
        mCountDownTimer.start()

        tv_splash_timer.setOnClickListener {
            jumpActivity(MainActivity::class.java)
            finish()
        }
    }

    override fun onDestroy() {

        mCountDownTimer.cancel()
        super.onDestroy()
    }
}