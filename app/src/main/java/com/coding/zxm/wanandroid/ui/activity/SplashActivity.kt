package com.coding.zxm.wanandroid.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.CountDownTimer
import androidx.core.app.ActivityCompat
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.MainActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.login.LoginActivity
import com.zxm.utils.core.dialog.DialogUtil
import com.zxm.utils.core.permission.PermissionChecker
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/28 . All rights reserved.
 */
class SplashActivity : BaseActivity() {

    private lateinit var mCountDownTimer: CountDownTimer

    private val permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun setLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initParamsAndValues() {
        setStatusBarColorWhite()
    }

    override fun initViews() {

        mCountDownTimer = object : CountDownTimer(5 * 1000, 1000) {
            override fun onFinish() {
                jumpHome()
            }

            override fun onTick(millisUntilFinished: Long) {
                val time = millisUntilFinished / 1000
                tv_splash_timer.text = String.format("%ss", time)
            }

        }

        checkPermissions()

        tv_splash_timer.setOnClickListener {
            jumpHome()
        }

        tv_try.setOnClickListener {
            jumpHome()
        }
    }

    private fun checkPermissions() {
        if (!PermissionChecker.checkSeriesPermissions(mContext!!, permissions)) {
            val deniedPermissions =
                PermissionChecker.checkDeniedPermissions(mContext!!, permissions)

            if (deniedPermissions != null && deniedPermissions.isNotEmpty()) {
                PermissionChecker.requestPermissions(this, deniedPermissions, 1001)
            }

        } else {
            mCountDownTimer.start()
        }
    }

    private fun jumpHome() {
        val intent = Intent(mContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun jumpLogin() {
        val intent = Intent(mContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1001 -> {
                if (grantResults != null) {
                    val size = grantResults.size
                    for (i in 0 until size) {
                        val grantResult = grantResults[i]
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            val showRequest =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                    this@SplashActivity, permissions[i]!!
                                )
                            if (showRequest) {
                                DialogUtil.showForceDialog(
                                    mContext!!,
                                    PermissionChecker.matchRequestPermissionRationale(
                                        mContext!!,
                                        permissions[i]!!
                                    )
                                ) { dialog, which -> }
                            }
                        } else {
                        }
                    }
                }
                mCountDownTimer.start()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {

        mCountDownTimer.cancel()
        super.onDestroy()
    }
}