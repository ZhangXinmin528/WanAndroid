package com.coding.zxm.wanandroid

import android.view.View
import androidx.annotation.ColorRes
import com.coding.zxm.core.base.BaseFragment

/**
 * Created by ZhangXinmin on 2021/04/28.
 * Copyright (c) 4/28/21 . All rights reserved.
 */
abstract class BaseStatusBarFragment : BaseFragment() {

    fun setFakeStatusColor(@ColorRes color: Int) {
        if (rootView != null) {
            val fakeBar = rootView.findViewById<View>(R.id.fake_status_bar)
            fakeBar?.setBackgroundColor(resources.getColor(color))

        }
    }
}