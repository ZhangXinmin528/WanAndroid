package com.coding.zxm.wanandroid.system.repository

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import com.coding.zxm.wanandroid.system.service.KnowledgeService

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
class KnowledgeRepository(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * Request knowledge tree data.
     */
    suspend fun getKnowledgeTree(): CommonResult<MutableList<KnowledgeEntity>> {
        return onCall { requestknowledgeTree() }
    }

    private suspend fun requestknowledgeTree(): CommonResult<MutableList<KnowledgeEntity>> {
        return executeResponse(createService(KnowledgeService::class.java).getKnowledgeTree())
    }

    /**
     * 请求知识体系二级数据
     */
    suspend fun getKnowledgeArticles(
        @IntRange(from = 0) pageIndex: Int,
        id: Int
    ): CommonResult<NewsEntity> {
        return requestKnowledgeArticles(pageIndex, id)
    }

    private suspend fun requestKnowledgeArticles(
        @IntRange(from = 0) pageIndex: Int,
        id: Int
    ): CommonResult<NewsEntity> {
        return executeResponse(
            createService(KnowledgeService::class.java).getKnowledgeArticles(
                pageIndex,
                id
            )
        )
    }
}