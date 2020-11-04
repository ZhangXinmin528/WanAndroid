package com.coding.zxm.weather.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.coding.zxm.weather.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/4 . All rights reserved.
 */
class AQIView : View {

    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initParams(context, attrs)
    }

    private lateinit var mContext: Context

    //背景圆弧
    private lateinit var mLinePaint: Paint
    private var mColorBgArc: Int = Color.parseColor("#EDEDED")
    private var mWidthBgArc: Int = 10

    //指数圆弧
    private lateinit var mIndicatorPaint: Paint
    private var mColorIndicatorArc: Int = Color.WHITE

    //文本
    private lateinit var mTextPaint: Paint
    private var mColorText: Int = Color.WHITE
    private var mTextSize: Int = 20

    private fun initParams(context: Context?, attrs: AttributeSet?) {
        context?.let { mContext = it }

        attrs?.let {
            val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.StyleAQIView)
            if (typedArray != null) {
                //背景圆弧
                mColorBgArc = typedArray.getColor(
                    R.styleable.StyleAQIView_color_bg_arc_line,
                    Color.parseColor("#EDEDED")
                )
                mWidthBgArc =
                    typedArray.getDimensionPixelSize(
                        R.styleable.StyleAQIView_width_bg_arc_line,
                        10
                    )

                //指示圆弧
                mColorIndicatorArc = typedArray?.getColor(
                    R.styleable.StyleAQIView_color_aqi_arc_line,
                    Color.parseColor("#95B359")
                )

                //文本颜色
                mColorText =
                    typedArray.getColor(R.styleable.StyleAQIView_color_aqi_text, Color.WHITE)
                mTextSize = typedArray.getDimensionPixelSize(
                    R.styleable.StyleAQIView_textsize_aqi_text,
                    sp2px(mContext, 24f)
                )
                typedArray.recycle()
            }
        }

        //paint
        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint.setColor(mColorBgArc)
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = mWidthBgArc / 2.0f

    }

    /**
     * Value of sp to value of px.
     *
     * @param context context
     * @param spValue The value of sp.
     * @return value of px
     */
    fun sp2px(
        context: Context,
        @FloatRange(from = 0.0) spValue: Float
    ): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}