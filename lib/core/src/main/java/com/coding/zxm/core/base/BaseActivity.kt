package com.coding.zxm.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
abstract class BaseActivity: AppCompatActivity() {
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

}