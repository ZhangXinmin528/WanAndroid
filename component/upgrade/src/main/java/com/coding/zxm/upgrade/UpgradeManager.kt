package com.coding.zxm.upgrade

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.IBinder
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.coding.zxm.upgrade.entity.UpdateEntity
import com.coding.zxm.upgrade.provider.IUpgradeProvider
import java.io.File


/**
 * Created by ZhangXinmin on 2021/05/17.
 * Copyright (c) 5/17/21 . All rights reserved.
 * 应用更新
 */
class UpgradeManager private constructor(val builder: UpgradeBuilder) {

    companion object {
        private const val TAG = "UpgradeManager"

        private var mUpgradeLivedata: MutableLiveData<UpdateEntity> = MutableLiveData()

    }

    /**
     * 检测版本更新
     */
    fun checkUpgrade() {

        UpgradeService.bindService(builder.activity!!, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as UpgradeService.UpgradeBinder).checkUpgrade(
                    builder.upgradeProvider,
                    builder.upgradeToken,
                    builder.apkName
                )
            }
        })

    }

    /**
     * 下载新版本Apk
     */
    fun downloadApk(downloadUrl: String?) {
        UpgradeService.bindService(builder.activity!!, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {

            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                (service as UpgradeService.UpgradeBinder).downloadApk(
                    downloadUrl,
                    builder.upgradeProvider
                )
            }

        })
    }

    /**
     * 是否存在新版本
     */
    private fun hasNewVersion(@NonNull entity: UpdateEntity?): Boolean {
        if (entity != null) {
            val currVersion = getAppVersionCode(builder.activity)
            val newVersion = entity.version
            if (!TextUtils.isEmpty(newVersion) && currVersion != -1) {
                return newVersion!!.toInt() > currVersion
            }
        }
        return false
    }

    /**
     * install new version apk
     *
     * @param file
     */
    private fun installNewApkWithNoRoot(context: Context, file: File) {
        if (file.exists()) {
            Toast.makeText(context, "正在安装更新...", Toast.LENGTH_SHORT).show()
            val uri: Uri = Uri.fromFile(file)
            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val contentUri: Uri = FileProvider
                    .getUriForFile(
                        context,
                        context.applicationContext.packageName + ".fileProvider",
                        file
                    )
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    /**
     * Get app version code
     */
    private fun getAppVersionCode(context: Context?): Int {
        val packageManager = context?.packageManager
        if (packageManager != null) {
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        }
        return -1
    }


    /**
     * Checks if external storage is available for read and write
     */
    fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    class UpgradeBuilder {
        var activity: FragmentActivity? = null
        var upgradeToken: String? = null
        var upgradeProvider: IUpgradeProvider? = null
        var apkName: String? = null

        fun setActivity(activity: FragmentActivity): UpgradeBuilder {
            this.activity = activity
            return this
        }

        fun setUpgradeToken(token: String): UpgradeBuilder {
            this.upgradeToken = token
            return this
        }

        fun setUpgradeProvider(provider: IUpgradeProvider): UpgradeBuilder {
            upgradeProvider = provider
            return this
        }

        fun setApkName(name: String): UpgradeBuilder {
            apkName = name
            return this
        }

        fun build(): UpgradeManager {
            if (activity == null || TextUtils.isEmpty(upgradeToken)
                || upgradeProvider == null
            ) {
                throw NullPointerException("关键参数为空！")
            }
            return UpgradeManager(this)
        }
    }
}