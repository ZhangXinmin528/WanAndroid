package com.coding.zxm.lib.qrcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.annotation.IntRange
import com.coding.libzxing.activity.CaptureActivity
import com.coding.libzxing.encoding.QRCodeEncoder

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

        /**
         * 创建带有logo的二维码
         */
        fun generateQRCodeWithLogo(
            context: Context,
            size: Float,
            content: String,
            logoBitmap: Bitmap
        ): Bitmap {
            val width = QRCodeEncoder.dp2px(context, size)
            return QRCodeEncoder.createCommonQRCodeWithLogo(content, width, logoBitmap)
        }

        /**
         * 创建二维码
         */
        fun generateQRCode(
            context: Context,
            size: Float,
            content: String,
        ): Bitmap {
            val width = QRCodeEncoder.dp2px(context, size)
            return QRCodeEncoder.createCommonQRCodeNoLogo(content, width)
        }
    }
}