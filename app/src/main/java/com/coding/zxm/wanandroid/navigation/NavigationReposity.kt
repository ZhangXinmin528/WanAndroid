package com.coding.zxm.wanandroid.navigation

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.navigation.model.NaviEntity

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
class NavigationReposity(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * 获取导航数据
     */
    suspend fun getNavigationData(): CommonResponse<MutableList<NaviEntity>> {
        return creatService(NavigationService::class.java).getNavigationData()
    }

}