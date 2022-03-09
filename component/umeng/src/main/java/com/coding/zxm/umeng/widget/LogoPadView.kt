package com.coding.zxm.umeng.widget

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.coding.zxm.umeng.R

/**
 * Created by ZhangXinmin on 2022/03/09.
 * Copyright (c) 2022/3/9 . All rights reserved.
 * 面板控件
 */
class LogoPadView : ConstraintLayout {


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private var rootLayout: View? = null
    private var logoView: ImageView? = null
    private var logoName: TextView? = null

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        rootLayout = LayoutInflater.from(context).inflate(R.layout.layout_logo_pad, this, true)
        logoView = rootLayout?.findViewById(R.id.iv_logo)
        logoName = rootLayout?.findViewById(R.id.tv_logo_name)
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.LogoPadView, defStyleAttr, 0)

            val logoResId = typedArray.getResourceId(
                R.styleable.LogoPadView_logoRes,
                R.drawable.umeng_socialize_qq
            )

            val logoNameStr = typedArray.getString(R.styleable.LogoPadView_logoName)

            if (logoResId != 0) {
                logoView?.setImageResource(logoResId)
            }
            if (!TextUtils.isEmpty(logoNameStr)) {
                logoName?.text = logoNameStr
            }

            typedArray.recycle()

        }

    }

    fun setLogoBitmap(logo: Bitmap) {
        logoView?.setImageBitmap(logo)
    }

    fun setLogoNameColor(@ColorInt color: Int) {
        logoName?.setTextColor(color)
    }

}