package com.coding.zxm.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    protected var mContext: Context? = null

    abstract fun setLayoutId(): Int;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutId());

        mContext = this

        initParamsAndValues()

        initViews()

    }

    abstract fun initParamsAndValues()

    abstract fun initViews()

    protected fun jumpActivity(intent: Intent) {
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    protected fun jumpActivity(clazz: Class<*>) {
        val intent = Intent(mContext, clazz)
        jumpActivity(intent)
    }

    protected fun jumpActivity(bundle: Bundle, clazz: Class<*>) {
        val intent = Intent(mContext, clazz)
        intent.putExtras(bundle)
        jumpActivity(intent)
    }
}