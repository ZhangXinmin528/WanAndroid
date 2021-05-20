package com.coding.zxm.upgrade

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.text.TextUtils
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import com.coding.zxm.upgrade.callback.FileDownloadCallback
import com.coding.zxm.upgrade.provider.IUpgradeProvider
import com.coding.zxm.upgrade.utils.UpgradeUtils
import java.io.File
import kotlin.math.roundToInt

/**
 * Created by ZhangXinmin on 2021/05/19.
 * Copyright (c) 5/19/21 . All rights reserved.
 * 用户控制更新包的下载
 */
class UpgradeService : Service() {

    companion object {
        private var isDownloading: Boolean = false
        private const val TAG = "UpgradeService"
        private var context: Context? = null
        private var conn: ServiceConnection? = null

        //Notification
        private const val NOTIFY_ID: Int = 1001
        private const val CHANNEL_ID = "app_update_id"
        private const val CHANNEL_NAME: String = "app_update_channel"
        private var mNotificationManager: NotificationManager? = null
        private var mBuilder: NotificationCompat.Builder? = null

        fun bindService(context: Context, connection: ServiceConnection) {
            this.context = context
            val intent = Intent(context, UpgradeService::class.java)
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        fun unBindService(context: Context) {
            conn?.let { context.unbindService(it) }
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind()")
        return UpgradeBinder()
    }

    override fun unbindService(conn: ServiceConnection) {
        Log.d(TAG, "unbindService()")
        super.unbindService(conn)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        mNotificationManager = null
        super.onDestroy()
    }

    class UpgradeBinder : Binder() {
        fun checkUpgrade(
            @NonNull provider: IUpgradeProvider?,
            token: String?,
            apkName: String?
        ) {
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(apkName)) {
                provider?.checkUpgrade(token!!, apkName!!)
            }
        }

        fun downloadApk(
            downloadUrl: String?,
            provider: IUpgradeProvider?
        ) {

            if (provider != null) {
                val apkFile = provider.getNewApkFile()

                if (apkFile != null && apkFile.exists()) {
                    UpgradeUtils.installNewApkWithNoRoot(context!!, apkFile)
                } else {
                    if (!TextUtils.isEmpty(downloadUrl)) {
                        setUpNotification()
                        if (!isDownloading) {
                            provider.downloadApk(downloadUrl, object : FileDownloadCallback {
                                override fun onDownloadFailed(msg: String?) {
                                    Log.e(TAG, "文件下载失败：$msg")
                                    isDownloading = false
                                }

                                override fun inProgress(progress: Float, totalSize: Long) {
                                    isDownloading = true
                                    if (mBuilder != null) {
                                        val rate = (progress * 100).roundToInt()
                                        mBuilder?.setContentTitle(
                                            "正在下载：${UpgradeUtils.getAppName(
                                                context
                                            )}"
                                        )
                                            ?.setContentText("$rate%")
                                            ?.setProgress(100, rate, false)
                                            ?.setWhen(System.currentTimeMillis())

                                        val notification = mBuilder?.build()
                                        notification?.flags =
                                            Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONLY_ALERT_ONCE

                                        mNotificationManager?.notify(NOTIFY_ID, notification)
                                    }
                                }

                                override fun onDownloadFinished(file: File) {
                                    isDownloading = false
                                    //TODO:前台后台逻辑完善
                                    UpgradeUtils.installNewApkWithNoRoot(
                                        context!!,
                                        provider.getNewApkFile()
                                    )

                                    unBindService(context!!)
                                }


                            })
                        }
                    }
                }
            }
        }

        /**
         * 创建通知
         */
        private fun setUpNotification() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )

                channel.enableLights(false)
                channel.enableVibration(false)

                mNotificationManager?.createNotificationChannel(channel)
            }

            mBuilder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            val iconDrawable = UpgradeUtils.getAppIcon(context!!)
            mBuilder?.setContentTitle("开始下载")
                ?.setContentText("正在连接服务器")
                ?.setSmallIcon(R.mipmap.lib_update_app_update_icon)
                ?.setLargeIcon(iconDrawable?.let { UpgradeUtils.drawableToBitmap(it) })
                ?.setOngoing(true)
                ?.setAutoCancel(true)
                ?.setWhen(System.currentTimeMillis())

            mNotificationManager?.notify(NOTIFY_ID, mBuilder!!.build())

        }
    }


}