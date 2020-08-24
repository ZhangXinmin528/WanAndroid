package com.coding.zxm.wanandroid.mine

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.mine.model.UserDetialEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
class MineRepository(private val client: RetrofitClient) : BaseRepository() {

    suspend fun getUserInfo(): NetworkResult<UserDetialEntity> {
        return onRequest(call = { requestUserInfo() })
    }

    private suspend fun requestUserInfo(): NetworkResult<UserDetialEntity> {
        return onResponse(client.create(MineService::class.java).getUserInfo())
    }
}