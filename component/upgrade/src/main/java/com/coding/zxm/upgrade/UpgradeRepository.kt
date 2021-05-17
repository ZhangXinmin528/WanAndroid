package com.coding.zxm.upgrade

import androidx.annotation.NonNull
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * Created by ZhangXinmin on 2021/05/14.
 * Copyright (c) 5/14/21 . All rights reserved.
 */
class UpgradeRepository(client: RetrofitClient) : BaseRepository(client) {

    /**
     * 检查更新
     */
    suspend fun checkUpgrade(@NonNull token: String): CommonResult<UpdateEntity> {
        return onCall { checkUpdateInfo(token) }
    }

    private suspend fun checkUpdateInfo(token: String): CommonResult<UpdateEntity> {
        return excuteUpgradeResponse(creatService(UpgradeService::class.java).checkUpdate(token))
    }

    private suspend fun excuteUpgradeResponse(
        response: UpdateEntity,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): CommonResult<UpdateEntity> {
        return coroutineScope {
            if (response == null) {
                errorBlock?.let { it() }
                CommonResult.Error(Exception("网络请求异常"))
            } else {
                successBlock?.let { it() }
                CommonResult.Success(response)
            }
        }

    }
}