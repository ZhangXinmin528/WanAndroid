package com.coding.zxm.wanandroid.mine

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.mine.model.UserDetialEntity
import retrofit2.http.GET

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
interface MineService {

    /**
     * 获取个人积分，需要先登录~
     */
    @GET("/lg/coin/userinfo/json")
    suspend fun getUserInfo(): CommonResponse<UserDetialEntity>
}