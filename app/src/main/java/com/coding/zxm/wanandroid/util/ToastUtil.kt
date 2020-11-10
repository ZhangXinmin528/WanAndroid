package com.coding.zxm.wanandroid.util

import android.text.TextUtils
import android.widget.Toast
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.app.WanApp

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/10 . All rights reserved.
 */
class ToastUtil private constructor() {
    companion object {
        /**
         * Toast
         */
        fun showToast(msg: String) {
            if (!TextUtils.isEmpty(msg)) {
                Toast.makeText(WanApp.getApplicationContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * Toast
         */
        fun showUnKnownError() {
            Toast.makeText(WanApp.getApplicationContext(), WanApp.getApplicationContext().getString(
                R.string.all_exception_unknown), Toast.LENGTH_SHORT).show()
        }
    }
}