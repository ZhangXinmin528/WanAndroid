package com.coding.zxm.weather.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import com.coding.zxm.weather.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/4 . All rights reserved.
 * 空气质量指数
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
    private var mWidthBgArc: Float = 10.0f

    //指数圆弧
    private lateinit var mIndicatorPaint: Paint
    private var mColorIndicatorArc: Int = Color.WHITE

    //文本
    private lateinit var mTextPaint: Paint
    private var mColorText: Int = Color.WHITE
    private var mTextSize: Float = 20.0f

    //尺寸
    private var mDefaultSize: Float = 0.0f
    private var mDefaultPadding: Float = 0.0f
    private var mViewWidth: Float = 0.0f
    private var mViewHeight: Float = 0.0f

    private var mCenterX: Float = 0.0f
    private var mCenterY: Float = 0.0f
    private var mRadius: Float = 0.0f

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
                    typedArray.getDimension(
                        R.styleable.StyleAQIView_width_bg_arc_line,
                        10f
                    )

                //指示圆弧
                mColorIndicatorArc = typedArray.getColor(
                    R.styleable.StyleAQIView_color_aqi_arc_line,
                    Color.parseColor("#95B359")
                )

                //文本颜色
                mColorText =
                    typedArray.getColor(R.styleable.StyleAQIView_color_aqi_text, Color.WHITE)
                mTextSize = typedArray.getDimension(
                    R.styleable.StyleAQIView_textsize_aqi_text,
                    sp2px(mContext, 24f).toFloat()
                )
                typedArray.recycle()
            }
        }

        //paint
        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint.setColor(mColorBgArc)
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.strokeWidth = mWidthBgArc / 2.0f

        mIndicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mIndicatorPaint.setColor(mColorIndicatorArc)
        mIndicatorPaint.style = Paint.Style.STROKE
        mIndicatorPaint.strokeWidth = mWidthBgArc

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.setColor(mColorText)
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = mTextSize

        mDefaultSize = dp2px(mContext, 100f)
        mDefaultPadding = dp2px(mContext, 6f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)

        //宽度
        mViewWidth = if (widthMode == MeasureSpec.EXACTLY) {//match_parent or 具体数值
            MeasureSpec.getSize(widthMeasureSpec).toFloat()
        } else {//指定尺寸
            mDefaultSize
        }

        //高度
        mViewHeight = if (heightMode == MeasureSpec.EXACTLY) {//match_parent or 具体数值
            MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {//指定尺寸
            mDefaultSize
        }

        mCenterX = mViewWidth / 2.0f
        mCenterY = mViewHeight / 2.0f

        mRadius = if (mViewHeight > mViewWidth) (mViewWidth - mDefaultPadding * 2) / 2.0f else
            (mViewHeight - mDefaultPadding * 2) / 2.0f

        super.onMeasure(mViewWidth.toInt(), mViewHeight.toInt())
    }

    override fun onDraw(canvas: Canvas?) {

        //绘制封闭圆环
        canvas?.drawCircle(100f, 100f, 100f, mLinePaint)


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
    ): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toFloat()
    }

    /**
     * dp to px
     */
    fun dp2px(context: Context, @FloatRange(from = 0.0) dpValue: Float): Float {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toFloat()
    }
}