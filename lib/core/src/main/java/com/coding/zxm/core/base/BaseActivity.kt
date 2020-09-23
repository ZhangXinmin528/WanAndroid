package com.coding.zxm.core.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.coding.zxm.core.R
import com.coding.zxm.util.SharedPreferenceConfig
import com.zxm.utils.core.bar.StatusBarCompat
import com.zxm.utils.core.sp.SharedPreferencesUtil

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName

    protected var mContext: Context? = null

    abstract fun setLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayoutId())

        mContext = this

        initParamsAndValues()

    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            Log.d(TAG, "getResources()~")
            val config: Configuration = res.configuration
            config.fontScale =
                SharedPreferencesUtil.get(
                    this,
                    SharedPreferenceConfig.CONFIG_FONT_SCALE,
                    1.0f
                ) as Float
            res.updateConfiguration(config, res.displayMetrics)
        }
        return res
    }

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val res = newBase.resources
            val configuration = res.configuration

            Log.d(TAG, "attachBaseContext()~")
            configuration.let {

                it.fontScale = SharedPreferencesUtil.get(
                    newBase,
                    SharedPreferenceConfig.CONFIG_FONT_SCALE,
                    1.0f
                ) as Float

                val newContext = newBase.createConfigurationContext(it)
                super.attachBaseContext(newContext)
            }
        } else {
            super.attachBaseContext(newBase)
        }
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

    protected fun setStatusBarColor() {
        StatusBarCompat.setColor(this, resources.getColor(R.color.color_state_bar))
    }

    protected fun setStatusBarColorNoTranslucent() {
        StatusBarCompat.setColorNoTranslucent(this, resources.getColor(R.color.colorWhite))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    protected fun initActionBar(toolbar: Toolbar, titile: String) {
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = titile
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
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