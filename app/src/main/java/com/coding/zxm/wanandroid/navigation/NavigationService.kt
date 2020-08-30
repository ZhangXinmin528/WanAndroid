package com.coding.zxm.wanandroid.navigation

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.navigation.model.NaviEntity
import retrofit2.http.GET

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
interface NavigationService {

    @GET("/navi/json")
    suspend fun getNavigationData(): CommonResponse<MutableList<NaviEntity>>
}