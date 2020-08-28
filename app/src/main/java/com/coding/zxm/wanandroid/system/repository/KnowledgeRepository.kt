package com.coding.zxm.wanandroid.system.repository

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import com.coding.zxm.wanandroid.system.service.KnowledgeService

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
class KnowledgeRepository(private val client: RetrofitClient) : BaseRepository() {

    /**
     * Request knowledge tree data.
     */
    suspend fun getKnowledgeTree(): NetworkResult<MutableList<KnowledgeEntity>> {
        return onRequest(call = { requestKnowledgeTree() })
    }

    private suspend fun requestKnowledgeTree(): NetworkResult<MutableList<KnowledgeEntity>> {
        return onResponse(client.create(KnowledgeService::class.java).getKnowledgeTree())
    }

    /**
     * 请求知识体系二级数据
     */
    suspend fun getKnowledgeArticles(
        @IntRange(from = 0) pageIndex: Int,
        id: Int
    ): NetworkResult<NewsEntity> {
        return onRequest(call = { requestKnowledgeArticles(pageIndex, id) })
    }

    private suspend fun requestKnowledgeArticles(
        @IntRange(from = 0) page: Int,
        id: Int
    ): NetworkResult<NewsEntity> {
        return onResponse(
            client.create(KnowledgeService::class.java).getKnowledgeArticles(page, id)
        )
    }
}