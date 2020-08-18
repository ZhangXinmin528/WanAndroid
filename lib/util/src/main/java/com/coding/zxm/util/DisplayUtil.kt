package com.coding.zxm.util

import android.text.TextUtils

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/17 . All rights reserved.
 */
class DisplayUtil private constructor() {

    companion object {

    }

    fun isEmpty(value: String): String {
        return if (!TextUtils.isEmpty(value)) {
            value
        } else {
            ""
        }
    }
}