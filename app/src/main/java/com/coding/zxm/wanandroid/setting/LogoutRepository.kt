package com.coding.zxm.wanandroid.setting

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResponse

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class LogoutRepository(client: RetrofitClient) : BaseRepository(client = client) {

    suspend fun logout(): CommonResponse<Any> {
        return createService(LogoutService::class.java).logout()
    }

}