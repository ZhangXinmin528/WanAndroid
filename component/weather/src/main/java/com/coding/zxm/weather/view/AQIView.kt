package com.coding.zxm.weather.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.FloatRange
import com.coding.zxm.weather.R
import com.coding.zxm.weather.util.WeatherUtil
import com.qweather.sdk.bean.air.AirNowBean
import com.zxm.utils.core.log.MLogger

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/4 . All rights reserved.
 * 空气质量指数
 * <p>
 *     View 尺寸最小120dp
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
    private lateinit var mBgArcPaint: Paint
    private var mColorBgArc: Int = Color.parseColor("#EDEDED")
    private var mWidthBgArc: Float = 10.0f

    //指数圆弧
    private lateinit var mIndicArcPaint: Paint
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

    //AQI
    private var mAQIValueHeight: Float = 0.0f

    private var mCenterX: Float = 0.0f
    private var mCenterY: Float = 0.0f
    private var mRadius: Float = 0.0f
    private var mRectF: RectF? = null

    private var mAqi: String = ""
    private var mAqiValue: Int = 0

    private var mCategory: String = ""

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
                    Color.WHITE
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
        mBgArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBgArcPaint.color = mColorBgArc
        mBgArcPaint.style = Paint.Style.STROKE
        mBgArcPaint.strokeCap = Paint.Cap.ROUND
        mBgArcPaint.strokeWidth = mWidthBgArc / 2.0f

        mIndicArcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mIndicArcPaint.color = mColorIndicatorArc
        mIndicArcPaint.style = Paint.Style.STROKE
        mIndicArcPaint.strokeCap = Paint.Cap.ROUND
        mIndicArcPaint.strokeWidth = mWidthBgArc

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint.color = mColorText
        mTextPaint.style = Paint.Style.FILL
        mTextPaint.textSize = mTextSize

        //AQI value
        val fontMetrics = mTextPaint.fontMetrics
        mAQIValueHeight = (fontMetrics.bottom - fontMetrics.top) / 2.0f - fontMetrics.bottom

        mDefaultSize = dp2px(mContext, 120f)
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

        mViewWidth = if (mViewWidth < mDefaultSize) {
            mDefaultSize
        } else {
            mViewWidth
        }

        //高度
        mViewHeight = if (heightMode == MeasureSpec.EXACTLY) {//match_parent or 具体数值
            MeasureSpec.getSize(heightMeasureSpec).toFloat()
        } else {//指定尺寸
            mDefaultSize
        }

        mViewHeight = if (mViewHeight < mDefaultSize) {
            mDefaultSize
        } else {
            mViewHeight
        }

        mCenterX = mViewWidth / 2.0f
        mCenterY = mViewHeight / 2.0f

        mRadius = if (mViewHeight > mViewWidth) (mViewWidth - mDefaultPadding * 2) / 2.0f else
            (mViewHeight - mDefaultPadding * 2) / 2.0f

        if (mRectF == null && mRadius != 0f) {
            mRectF = RectF(
                mCenterX - mRadius,
                mCenterY - mRadius,
                mCenterX + mRadius,
                mCenterY + mRadius
            )

        }
//        MLogger.d(
//            "AQIView",
//            "onMeasure..widthMode:$widthMode..heightMode:$heightMode..mViewWidth:$mViewWidth..mViewHeight:$mViewHeight"
//        )

        setMeasuredDimension(mViewWidth.toInt(), mViewHeight.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            //绘制封闭圆环
            mBgArcPaint.color = mColorBgArc
            mBgArcPaint.strokeWidth = mWidthBgArc / 2.0f

            it.drawCircle(mCenterX, mCenterY, mRadius, mBgArcPaint)

            //绘制文本
            if (!TextUtils.isEmpty(mAqi) && !TextUtils.isEmpty(mCategory)) {

                //绘制指示圆弧
                mIndicArcPaint.color = mColorIndicatorArc
                mIndicArcPaint.strokeWidth = mWidthBgArc

                val aqiValue = if (mAqiValue >= 500) {
                    500
                } else {
                    mAqiValue
                }

                mRectF?.let { rectF ->
                    it.drawArc(rectF, -247.5f, 315f * aqiValue / 500, false, mIndicArcPaint)
                }

                //绘制AQI value
                mTextPaint.textSize = mTextSize
                mTextPaint.color = mColorIndicatorArc
                val contentWidth = mTextPaint.measureText(mAqi)
                it.drawText(
                    mAqi,
                    mCenterX - contentWidth / 2.0f,
                    mCenterY + mAQIValueHeight,
                    mTextPaint
                )

                //绘制 AQI category
                mTextPaint.textSize = mTextSize / 2.0f

                val category = if (mCategory.length == 4) {
                    mCategory.substring(0, 2)
                } else {
                    mCategory
                }
                val categoryWidth = mTextPaint.measureText(category)

                val fontMetrics = mTextPaint.fontMetrics
                val categoryHeight =
                    (fontMetrics.bottom - fontMetrics.top) / 2.0f - fontMetrics.bottom

                it.drawText(
                    category,
                    mCenterX - categoryWidth / 2.0f,
                    mCenterY + mRadius * 0.5f + categoryHeight,
                    mTextPaint
                )

                //绘制 AQI
                mTextPaint.color = mColorText
                val aqiWidth = mTextPaint.measureText("AQI")

                it.drawText(
                    "AQI",
                    mCenterX - aqiWidth / 2.0f,
                    mCenterY - mAQIValueHeight - mDefaultPadding - categoryHeight,
                    mTextPaint
                )

            }
        }

    }

    fun setAQIData(airNowBean: AirNowBean) {
        if (airNowBean != null) {
            try {
                mAqi = airNowBean.now.aqi
                mCategory = airNowBean.now.category

                if (TextUtils.isEmpty(mAqi) || TextUtils.isEmpty(mCategory)) {
                    return
                }

                mColorIndicatorArc =
                    mContext.resources.getColor(WeatherUtil.getAQIColorRes(airNowBean.now.level))

                val aqiAnimator = ValueAnimator.ofInt(mAqi.toInt())
                aqiAnimator.repeatCount = 0
                aqiAnimator.duration = 3000
                aqiAnimator.interpolator = DecelerateInterpolator(mContext, null)

                aqiAnimator.addUpdateListener {
                    mAqiValue = it.animatedValue as Int
                    postInvalidate()
                }
                aqiAnimator.start()

            } catch (exception: NumberFormatException) {
                MLogger.d("AQIView", exception.toString())
            }

        }
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