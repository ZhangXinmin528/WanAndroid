package com.coding.zxm.upgrade

import androidx.annotation.NonNull
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult

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
        return excuteResponse(creatService(UpgradeService::class.java).checkUpdate(token))
    }
}