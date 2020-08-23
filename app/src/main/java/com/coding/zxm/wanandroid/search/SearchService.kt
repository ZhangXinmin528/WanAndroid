package com.coding.zxm.wanandroid.search

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.search.model.HotWordEntity
import com.coding.zxm.wanandroid.search.model.SearchEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
interface SearchService {

    @GET("/hotkey/json")
    suspend fun getHotWord(): CommonResponse<MutableList<HotWordEntity>>

    @POST("/article/query/{page}/json")
    suspend fun doSearch(
        @Path("page") page: Int,
        @Query("k") k: String
    ): CommonResponse<SearchEntity>
}
