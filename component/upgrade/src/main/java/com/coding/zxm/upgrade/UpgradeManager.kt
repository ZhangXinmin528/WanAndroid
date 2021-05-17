package com.coding.zxm.upgrade

import android.content.Context
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
    fun checkUpgrade(version: String): MutableLiveData<UpdateEntity> {
        val liveData: MutableLiveData<UpdateEntity> = MutableLiveData()

        val call = RetrofitClient.getInstance(context)?.create(UpgradeService::class.java)
            ?.checkUpdate2("911a59ee1bfdd702ccdd1935bde1fe30")

        call?.let {
            it.enqueue(object : Callback<UpdateEntity> {
                override fun onFailure(call: Call<UpdateEntity>, t: Throwable) {
                    liveData.postValue(null)
                }

                override fun onResponse(
                    call: Call<UpdateEntity>,
                    response: Response<UpdateEntity>
                ) {
                    if (response.isSuccessful) {
                        val entity = response.body()
                        liveData.postValue(entity)
                    }
                }

            })

        }
        return liveData
    }

    /**
     * Get app version code
     */
    fun getAppVersionCode(context: Context): Int {
        val packageManager = context.packageManager
        if (packageManager != null) {
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        }
        return -1
    }
}