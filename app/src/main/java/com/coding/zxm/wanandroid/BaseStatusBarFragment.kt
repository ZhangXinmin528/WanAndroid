package com.coding.zxm.wanandroid

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import com.coding.zxm.core.base.BaseFragment

/**
 * Created by ZhangXinmin on 2021/04/28.
 * Copyright (c) 4/28/21 . All rights reserved.
 */
abstract class BaseStatusBarFragment : BaseFragment() {

    fun setFakeStatusColor(@NonNull fakeBar: View, @ColorRes color: Int) {
        fakeBar.setBackgroundColor(resources.getColor(color))
    }
}