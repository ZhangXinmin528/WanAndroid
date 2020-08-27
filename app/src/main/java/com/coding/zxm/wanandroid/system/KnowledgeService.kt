package com.coding.zxm.wanandroid.system

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import retrofit2.http.GET

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
interface KnowledgeService {

    @GET("/tree/json")
    suspend fun getKnowledgeTree(): CommonResponse<MutableList<KnowledgeEntity>>

}