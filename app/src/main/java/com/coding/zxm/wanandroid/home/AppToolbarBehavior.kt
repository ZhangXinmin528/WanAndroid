package com.coding.zxm.wanandroid.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.zxm.utils.core.screen.ScreenUtil
import kotlin.math.roundToInt


/**
 * Created by ZhangXinmin on 2021/04/27.
 * Copyright (c) 4/27/21 . All rights reserved.
 * 首页搜索栏的行为
 */
class AppToolbarBehavior : CoordinatorLayout.Behavior<ConstraintLayout> {

    private var offset = 0

    private var targetHeight: Int = 0

    constructor() : super()

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.let { targetHeight = ScreenUtil.dp2px(it, 180f) }
    }


    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ConstraintLayout,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ConstraintLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {

        targetHeight = if (targetHeight == 0) 200 else targetHeight

        offset += dyConsumed
        when {
            offset <= 0 -> {  //alpha为0
                child.background.alpha = 0
            }
            offset in 1 until targetHeight -> { //alpha为0到255
                val precent: Float = offset / targetHeight.toFloat()
                val alpha = (precent * 255).roundToInt()
                child.background.alpha = alpha
            }
            offset >= targetHeight -> {  //alpha为255
                child.background.alpha = 255
            }
        }
    }
}