package com.coding.zxm.upgrade

import android.content.Context
import android.text.TextUtils
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import com.coding.zxm.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ZhangXinmin on 2021/05/17.
 * Copyright (c) 5/17/21 . All rights reserved.
 * 应用更新
 */
class UpgradeManager private constructor(val context: Context) {

    companion object {
        private var sInstance: UpgradeManager? = null
        private var mUpgradeLivedata: MutableLiveData<UpdateEntity> = MutableLiveData()

        @Synchronized
        fun getInstance(context: Context): UpgradeManager? {
            if (sInstance == null) {
                sInstance = UpgradeManager(context)
            }
            return sInstance
        }
    }

    /**
     * 检测版本更新
     * @param
     */
    fun checkUpgrade() {

        val call = RetrofitClient.getInstance(context)?.create(UpgradeService::class.java)
            ?.checkUpdate2("911a59ee1bfdd702ccdd1935bde1fe30")

        call?.let {
            it.enqueue(object : Callback<UpdateEntity> {
                override fun onFailure(call: Call<UpdateEntity>, t: Throwable) {
                    mUpgradeLivedata.postValue(null)
                    //TODO:检查更新失败
                }

                override fun onResponse(
                    call: Call<UpdateEntity>,
                    response: Response<UpdateEntity>
                ) {
                    if (response.isSuccessful) {
                        val entity = response.body()
                        mUpgradeLivedata.postValue(entity)
                        if (hasNewVersion(mUpgradeLivedata)) {

                        } else {
                            //TODO：没有新版本
                        }
                    } else {
                        //TODO:检查更新失败
                        mUpgradeLivedata.postValue(null)
                    }
                }

            })

        }

    }

    fun downloadApk(downurl: String) {

    }

    /**
     * 是否存在新版本
     */
    private fun hasNewVersion(@NonNull liveData: MutableLiveData<UpdateEntity>): Boolean {
        if (liveData.value != null) {
            val entity = liveData.value
            if (entity != null) {
                val currVersion = getAppVersionCode(context)
                val newVersion = entity.version
                if (!TextUtils.isEmpty(newVersion) && currVersion != -1) {
                    return newVersion!!.toInt() > currVersion
                }
            }
        }
        return false
    }

    /**
     * Get app version code
     */
    private fun getAppVersionCode(context: Context): Int {
        val packageManager = context.packageManager
        if (packageManager != null) {
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        }
        return -1
    }
}