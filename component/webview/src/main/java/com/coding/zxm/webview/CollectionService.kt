package com.coding.zxm.webview

import com.coding.zxm.network.common.CommonResponse
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by ZhangXinmin on 2022/03/14.
 * Copyright (c) 2022/3/14 . All rights reserved.
 */
interface CollectionService {

    @POST("/lg/collect/{id}/json")
    suspend fun doCollect(
        @Path("id") id: String
    ): CommonResponse<Void>

    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun doUncollect(
        @Path("id") id: String
    ): CommonResponse<Void>
}