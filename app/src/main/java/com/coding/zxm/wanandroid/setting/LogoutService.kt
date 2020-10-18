package com.coding.zxm.wanandroid.setting

import com.coding.zxm.network.common.CommonResponse
import retrofit2.http.GET

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
interface LogoutService {

    @GET("/user/logout/json")
    suspend fun logout(): CommonResponse<Any>
}