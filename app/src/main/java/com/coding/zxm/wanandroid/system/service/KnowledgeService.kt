package com.coding.zxm.wanandroid.system.service

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
interface KnowledgeService {

    /**
     * 获取体系数据
     */
    @GET("/tree/json")
    suspend fun getKnowledgeTree(): CommonResponse<MutableList<KnowledgeEntity>>

    /**
     * 获取知识体系下二级目录文章
     * @param page
     * @param id 二级目录id
     */
    @GET("/article/list/{page}/json?cid={id}}")
    suspend fun getKnowledgeArticles(
        @Path("page") page: Int,
        @Path("id") id: Int
    ): CommonResponse<NewsEntity>
}