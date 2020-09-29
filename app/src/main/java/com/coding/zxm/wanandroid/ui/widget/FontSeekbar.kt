package com.coding.zxm.wanandroid.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.annotation.FloatRange
import com.coding.zxm.wanandroid.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/23 . All rights reserved.
 * 用于设置文字缩放大小
 */
class FontSeekbar :
    View, View.OnTouchListener {

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

    private lateinit var mShapePaint: Paint
    private var mDotColor: Int = Color.WHITE

    //刻度
    private var mScaleUnit: Float = 0f

    //刻度个数
    private val mScaleCount: Int = 4

    //标签
    private val mBigLabel = "大"
    private var mBigLabelHeight: Float = 0f
    private var mBigLabelWight: Float = 0f
    private val mNormalLabel = "标准"
    private var mNormalLabelHeight: Float = 0f
    private var mNormalLabelWight: Float = 0f
    private val mSmallLabel = "小"
    private var mSmallLabelHeight: Float = 0f
    private var mSmallLabelWight: Float = 0f

    private var mScaleLineStartY: Float = 0f

    private var dp16: Int = 0
    private var dp12: Int = 0
    private var dp8: Int = 0
    private var dp6: Int = 0
    private var dp4: Int = 0

    //刻度索引:默认为标准
    private var mScaleIndex: Int = 2

    private lateinit var mGestureDetector: GestureDetector
    private var mTouchSlop: Int = 0
    private lateinit var mOnScaleCallback: OnScaleCallback

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
                mDotColor = typedArray.getColor(
                    R.styleable.FontSeekbarStyleable_font_dot_color,
                    Color.WHITE
                )

                typedArray.recycle()
            }

            mTouchSlop = ViewConfiguration.get(mContext).scaledTouchSlop

        }

        mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLinePaint.strokeWidth = mLineWidth
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.color = mLineColor

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = mTextSizeNormal
        mTextPaint.color = mTextColor

        mShapePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mShapePaint.color = mDotColor
        mShapePaint.style = Paint.Style.FILL

        //normal
        mNormalLabelWight = mTextPaint.measureText(mNormalLabel)
        mNormalLabelHeight = mTextPaint.fontMetrics.bottom - mTextPaint.fontMetrics.top

        //big
        mTextPaint.textSize = mTextSizeNormal * (1.2f)
        mBigLabelWight = mTextPaint.measureText(mBigLabel)
        mBigLabelHeight = mTextPaint.fontMetrics.bottom - mTextPaint.fontMetrics.top

        //small
        mTextPaint.textSize = mTextSizeNormal * (0.8f)
        mSmallLabelWight = mTextPaint.measureText(mSmallLabel)
        mSmallLabelHeight = mTextPaint.fontMetrics.bottom - mTextPaint.fontMetrics.top

        dp16 = dp2px(mContext, 16f)
        dp12 = dp2px(mContext, 12f)
        dp8 = dp2px(mContext, 8f)
        dp6 = dp2px(mContext, 6f)
        dp4 = dp2px(mContext, 4f)

        setOnTouchListener(this)

        mGestureDetector =
            GestureDetector(mContext, object : GestureDetector.SimpleOnGestureListener() {

                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    e?.let {
                        val x = it.x
                        val y = it.y
                        if (y >= mScaleLineStartY - 2 * mTouchSlop && y <= mScaleLineStartY + 2 * mTouchSlop) {
                            mScaleIndex = ((x - dp16) / mScaleUnit).toInt()
                            Log.d("zxm==", "点击位置：x:${x}..y:${y}..position:${mScaleIndex}")
                            postInvalidate()
                            if (mOnScaleCallback != null) {
                                mOnScaleCallback.onScale(getScaleValue())
                            }
                        }
                    }
                    return super.onSingleTapUp(e)
                }
            })
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.let {
            it.drawColor(Color.WHITE)

            drawScale(canvas)
        }

    }

    private fun drawScale(canvas: Canvas) {

        canvas.translate(dp16.toFloat(), dp16.toFloat())

        //big label
        mTextPaint.textSize = mTextSizeNormal * (1.2f)
        val baseLineY =
            (mBigLabelHeight + dp16 * 2) / 2.0f + mBigLabelHeight / 2.0f - mTextPaint.fontMetrics.bottom

        canvas.drawText(mBigLabel, mWidth - mBigLabelWight / 2.0f, baseLineY, mTextPaint)

        //Normal
        mTextPaint.textSize = mTextSizeNormal
        canvas.drawText(
            mNormalLabel,
            mWidth / 2.0f - mNormalLabelWight / 2.0f,
            baseLineY,
            mTextPaint
        )

        //Small
        mTextPaint.textSize = mTextSizeNormal * (0.8f)
        canvas.drawText(mSmallLabel, 0f, baseLineY, mTextPaint)

        mScaleUnit = (mWidth - mBigLabelWight / 2.0f) / mScaleCount

        mScaleLineStartY =
            (mBigLabelHeight + dp16.toFloat() * 2.0f) + (mHeight - (mBigLabelHeight + dp16.toFloat() * 2.0f)) / 2.0f

        canvas.translate(mSmallLabelWight / 2.0f, mScaleLineStartY)

        mLinePaint.color = mLineColor
        canvas.drawLine(0f, dp4 / 2.0f, mWidth - mBigLabelWight / 2.0f, dp4 / 2.0f, mLinePaint)

        for (index in 0..mScaleCount) {
            canvas.drawLine(
                0f + index * mScaleUnit,
                0f,
                0f + index * mScaleUnit,
                dp4.toFloat(),
                mLinePaint
            )
        }

        //绘制圆点
        if (mScaleIndex < 0 || mScaleIndex > 4) return

        canvas.drawCircle(mScaleIndex * mScaleUnit, dp4 / 2.0f, dp8.toFloat(), mShapePaint)
        mLinePaint.color = Color.LTGRAY
        canvas.drawCircle(mScaleIndex * mScaleUnit, dp4 / 2.0f, dp6.toFloat(), mLinePaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mScreenHeight = MeasureSpec.getSize(heightMeasureSpec)
        mScreenWidth = MeasureSpec.getSize(widthMeasureSpec)

        mHeight = mScreenHeight - 2 * dp16
        mWidth = mScreenWidth - 2 * dp16

        setMeasuredDimension(mScreenWidth, mScreenHeight)
    }

    /**
     * 设置缩放刻度
     */
    fun setScaleValue(@FloatRange(from = 0.8, to = 1.2) scale: Float) {
        mScaleIndex = 2 + ((scale - 1.0f) * 10).toInt()
        postInvalidate()
    }

    fun getScaleValue(): Float {
        return 1 + (mScaleIndex - 2) * 0.1f
    }

    /**
     * 设置缩放回调
     */
    fun setOnScaleCallback(onScaleCallback: OnScaleCallback) {
        mOnScaleCallback = onScaleCallback
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

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        mGestureDetector.onTouchEvent(event)
        return true
    }

    interface OnScaleCallback {
        fun onScale(scale: Float)
    }
}