package com.coding.zxm.wanandroid.home

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.home.model.BannerEntity

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeRepository(private val client: RetrofitClient) : BaseRepository() {

    /**
     * Request home page banner data.
     */
    suspend fun getBannerData(): NetworkResult<MutableList<BannerEntity>> {
        return onRequest(call = { requestBanner() })
    }

    private suspend fun requestBanner(): NetworkResult<MutableList<BannerEntity>> {
        return onResponse(client.create(HomeService::class.java).getBannerData())
    }
}