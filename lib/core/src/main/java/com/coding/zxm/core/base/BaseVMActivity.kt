package com.coding.zxm.core.base

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseVMActivity<V : ViewModel> : BaseActivity() {

    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutId());

        mContext = this

        initParamsAndValues()

//        val clazz =
//            this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<T>
//        viewModel = ViewModelProvider(this).get<V>(types[0] as Class<V>)

        initViews()
    }

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