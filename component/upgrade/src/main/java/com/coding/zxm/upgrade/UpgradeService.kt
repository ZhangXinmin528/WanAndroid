package com.coding.zxm.upgrade

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder

/**
 * Created by ZhangXinmin on 2021/05/19.
 * Copyright (c) 5/19/21 . All rights reserved.
 * 用户控制更新包的下载
 */
class UpgradeService : Service() {

    private var mNotificationManager: NotificationManager? = null

    override fun onCreate() {
        super.onCreate()

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onBind(intent: Intent?): IBinder? {
        return UpgradeBinder()
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
    }

    override fun onDestroy() {
        mNotificationManager = null
        super.onDestroy()
    }

    class UpgradeBinder : Binder() {

    }
}