package com.coding.zxm.wanandroid.setting

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class LogoutRepository(private val client: RetrofitClient) : BaseRepository() {
    suspend fun logout(): NetworkResult<Any> {
        return onRequest(call = { requestLogout() })
    }

    private suspend fun requestLogout(): NetworkResult<Any> {
        return onResponse(client.create(LogoutService::class.java).logout())
    }


}