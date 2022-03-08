package com.coding.zxm.lib.qrcode

import android.app.Activity
import android.content.Intent
import androidx.annotation.IntRange
import com.coding.libzxing.activity.CaptureActivity

/**
 * Created by ZhangXinmin on 2022/03/08.
 * Copyright (c) 2022/3/8 . All rights reserved.
 */
class QrCodeUtils {
    companion object {

        /**
         * 扫一扫
         */
        fun startScanning(activity: Activity, @IntRange(from = 0) requestCode: Int) {
            val scan = Intent(activity, CaptureActivity::class.java)
            activity.startActivityForResult(scan, requestCode)
        }
    }
}