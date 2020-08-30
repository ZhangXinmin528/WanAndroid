package com.coding.zxm.wanandroid.navigation

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.navigation.model.NaviEntity

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
class NavigationReposity(private val client: RetrofitClient) : BaseRepository() {

    /**
     * 获取导航数据
     */
    suspend fun getNavigationData(): NetworkResult<MutableList<NaviEntity>> {
        return onRequest(call = { requestNavigationData() })
    }

    private suspend fun requestNavigationData(): NetworkResult<MutableList<NaviEntity>> {
        return onResponse(client.create(NavigationService::class.java).getNavigationData())
    }
}