package com.coding.zxm.wanandroid.home

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.HotWordEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity

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

    /**
     * Request home news data.
     */
    suspend fun getHomeNews(@IntRange(from = 0) pageIndex: Int): NetworkResult<NewsEntity> {
        return onRequest(call = { requestHomeNews(pageIndex) })
    }

    private suspend fun requestHomeNews(pageIndex: Int): NetworkResult<NewsEntity> {
        return onResponse(client.create(HomeService::class.java).getHomeList(pageIndex))
    }

}