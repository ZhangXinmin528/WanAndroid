package com.coding.zxm.wanandroid.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.FloatRange
import com.coding.zxm.wanandroid.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/23 . All rights reserved.
 * 用于设置文字缩放大小
 */
class FontSeekbar :
    View {

    constructor(context: Context?) : this(context, null, 0)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initParams(context, attrs)
    }

    private lateinit var mContext: Context

    private var mScreenHeight: Int = 0
    private var mScreenWidth: Int = 0

    private var mHeight: Int = 0
    private var mWidth: Int = 0

    private lateinit var mLinePaint: Paint
    private var mLineColor: Int = Color.parseColor("#747474")
    private var mLineWidth: Float = 1.0f

    private lateinit var mTextPaint: Paint
    private var mTextSizeNormal: Float = 14f
    private var mTextColor: Int = Color.BLACK

    //刻度
    private var mScaleUnit: Float = 0f

    //刻度个数
    private val mScaleCount: Int = 4

    private val mScaleValue: Float = 0.2f

    private var dp16: Int = 0
    private var dp12: Int = 0
    private var dp6: Int = 0

    @SuppressLint("CustomViewStyleable")
    private fun initParams(context: Context?, attrs: AttributeSet?) {
        if (context != null && attrs != null) {

            mContext = context

            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FontSeekbarStyleable)

            if (typedArray != null) {
                mLineColor = typedArray.getColor(
                    R.styleable.FontSeekbarStyleable_font_line_color,
                    Color.parseColor("#747474")
                )
                mLineWidth =
                    typedArray.getDimension(R.styleable.FontSeekbarStyleable_font_line_width, 1f)

                mTextSizeNormal = typedArray.getDimensionPixelSize(
                    R.styleable.FontSeekbarStyleable_font_textsize_normal,
                    14
                ).toFloat()

                mTextColor = typedArray.getColor(
                    R.styleable.FontSeekbarStyleable_font_text_color,
                    Color.BLACK
                )

                typedArray.recycle()
            }
            mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mLinePaint.strokeWidth = mLineWidth
            mLinePaint.style = Paint.Style.STROKE
            mLinePaint.color = mLineColor

            mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mTextPaint.style = Paint.Style.FILL
            mTextPaint.textSize = mTextSizeNormal
            mTextPaint.color = mTextColor

            dp16 = dp2px(mContext, 16f)
            dp12 = dp2px(mContext, 12f)
            dp6 = dp2px(mContext, 6f)


        }
        Log.d(
            "zxm==", "mLineWidth:${mLineWidth}..mLineColor:${mLineColor}" +
                    "..mTextSizeNormal:${mTextSizeNormal}..mTextColor:${mTextColor}"
        )
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.let {
            it.drawColor(Color.WHITE)

            drawScale(canvas)
        }

    }

    private fun drawScale(canvas: Canvas) {


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mScreenHeight = MeasureSpec.getSize(heightMeasureSpec)
        mScreenWidth = MeasureSpec.getSize(widthMeasureSpec)

        mHeight = mScreenHeight - 2 * dp12
        mWidth = mScreenWidth - 2 * dp12

        mScaleUnit = mWidth.toFloat() / mScaleCount

        setMeasuredDimension(mScreenWidth, mScreenHeight)
    }

    /**
     * dp to px
     */
    private fun dp2px(
        context: Context,
        @FloatRange(from = 0.0) dpValue: Float
    ): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * Value of sp to value of px.
     *
     * @param context context
     * @param spValue The value of sp.
     * @return value of px
     */
    private fun sp2px(
        context: Context,
        @FloatRange(from = 0.0) spValue: Float
    ): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}