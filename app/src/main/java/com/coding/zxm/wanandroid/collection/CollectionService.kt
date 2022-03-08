package com.coding.zxm.wanandroid.collection

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.home.model.NewsEntity
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by ZhangXinmin on 2022/03/08.
 * Copyright (c) 2022/3/8 . All rights reserved.
 */
interface CollectionService {

    @GET("/lg/collect/list/{pageIndex}/json")
    suspend fun getCollectionsList(@Path("pageIndex") pageIndex: Int): CommonResponse<NewsEntity>

}