package com.coding.zxm.wanandroid.system.repository

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResponse
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
    suspend fun getKnowledgeTree(): CommonResponse<MutableList<KnowledgeEntity>> {
        return creatService(KnowledgeService::class.java).getKnowledgeTree()
    }

    /**
     * 请求知识体系二级数据
     */
    suspend fun getKnowledgeArticles(
        @IntRange(from = 0) pageIndex: Int,
        id: Int
    ): CommonResponse<NewsEntity> {
        return creatService(KnowledgeService::class.java).getKnowledgeArticles(pageIndex, id)
    }

}