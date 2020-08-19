package com.coding.zxm.wanandroid.search

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.home.model.HotWordEntity
import retrofit2.http.GET

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
interface SearchService {

    @GET("/hotkey/json")
    suspend fun getHotWord(): CommonResponse<MutableList<HotWordEntity>>
}