package com.coding.zxm.wanandroid.home

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeRepository(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * Request home page banner data.
     */
    suspend fun getBannerData(): CommonResult<MutableList<BannerEntity>> {
        return onCall { requestBannerData() }
    }

    private suspend fun requestBannerData(): CommonResult<MutableList<BannerEntity>> {
        return executeResponse(createService(HomeService::class.java).getBannerData())
    }

    /**
     * Request home news data.
     */
    suspend fun getHomeNews(@IntRange(from = 0) pageIndex: Int): CommonResult<NewsEntity> {
        return onCall { requestHomeNews(pageIndex) }
    }

    private suspend fun requestHomeNews(@IntRange(from = 0) pageIndex: Int): CommonResult<NewsEntity> {
        return executeResponse(createService(HomeService::class.java).getHomeList(pageIndex))
    }

}