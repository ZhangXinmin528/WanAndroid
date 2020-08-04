package com.coding.zxm.wanandroid.ui.activity

import android.content.Intent
import android.os.CountDownTimer
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.login.LoginActivity
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
                jumpLogin()
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                tv_splash_timer.text = String.format("%ss", time)
            }

        }
        mCountDownTimer.start()

        tv_splash_timer.setOnClickListener {
            jumpLogin()
            finish()
        }
    }

    private fun jumpLogin() {
        val intent = Intent(mContext, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {

        mCountDownTimer.cancel()
        super.onDestroy()
    }
}