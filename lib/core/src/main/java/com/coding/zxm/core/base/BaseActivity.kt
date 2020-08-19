package com.coding.zxm.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.coding.zxm.core.R
import com.zxm.utils.core.bar.StatusBarCompat

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


    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    abstract fun initParamsAndValues()

    abstract fun initViews()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun setStateBarColor() {
        StatusBarCompat.setColor(this, resources.getColor(R.color.color_state_bar))
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