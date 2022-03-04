package com.coding.zxm.core.base

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.coding.zxm.core.R
import com.coding.zxm.util.LanguageUtil
import com.coding.zxm.util.SPConfig
import com.zxm.utils.core.bar.StatusBarCompat
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.sp.SharedPreferencesUtil

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 * Base activity~
 */
abstract class BaseActivity : AppCompatActivity() {
    protected val sTAG = this.javaClass.simpleName

    protected var mContext: Context? = null

    /**
     * Set content layout for the activity,if you will use the 'ViewBinding'.
     */
    abstract fun setContentLayout(): Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = setContentLayout()
        if (layout != null) {
            if (layout is Int) {
                setContentView(layout)
            } else if (layout is View) {
                setContentView(layout)
            } else {
                throw IllegalStateException("Content layout type is illegal!")
            }
        } else {
            throw NullPointerException("Content layout is null or empty!")
        }
        mContext = this

        LanguageUtil.setLanguageConfig(mContext!!)

        initParamsAndValues()

        initViews()
    }

    /**
     * 设置字体大小
     */
    override fun getResources(): Resources {
        val res = super.getResources()
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            val config: Configuration = res.configuration
            config.fontScale =
                SharedPreferencesUtil.get(
                    this,
                    SPConfig.CONFIG_FONT_SCALE,
                    1.0f
                ) as Float
            res.updateConfiguration(config, res.displayMetrics)
        }
        return res
    }

    /**
     * 设置字体大小
     */
    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            val res = newBase.resources
            val configuration = res.configuration

            configuration.let {

                it.fontScale = SharedPreferencesUtil.get(
                    newBase,
                    SPConfig.CONFIG_FONT_SCALE,
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        MLogger.d(sTAG, "onConfigurationChanged()~")
    }

    //===============================status bar ==============================================//
    protected fun setStatusBarColorPrimary() {
        StatusBarCompat.setColorNoTranslucent(
            this,
            resources.getColor(R.color.color_tool_bar_primary)
        )
    }

    protected fun setStatusBarColorLight() {
        StatusBarCompat.setColorNoTranslucent(this, resources.getColor(R.color.color_toolbar_light))
        StatusBarCompat.setStatusBarLightMode(this, true)
    }

    protected fun setStatusBarDark() {
        StatusBarCompat.setColor(this, resources.getColor(R.color.color_toolbar_dark))
        StatusBarCompat.setStatusBarLightMode(this, false)
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

    //========================================页面跳转=========================================//
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

    override fun overridePendingTransition(enterAnim: Int, exitAnim: Int) {
        super.overridePendingTransition(enterAnim, exitAnim)
    }

}