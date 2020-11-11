package com.coding.zxm.wanandroid.mine

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.mine.model.UserDetialEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
class MineRepository(client: RetrofitClient) : BaseRepository(client = client) {

    suspend fun getUserInfo(): CommonResponse<UserDetialEntity> {
        return creatService(MineService::class.java).getUserInfo()
    }
}