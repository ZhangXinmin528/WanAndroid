package com.coding.zxm.wanandroid.home

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
interface HomeService {

    @GET("/banner/json")
    suspend fun getBannerData(): CommonResponse<MutableList<BannerEntity>>

    @GET("/article/list/{pageIndex}/json")
    suspend fun getHomeList(@Path("pageIndex") pageIndex: Int): CommonResponse<NewsEntity>

}