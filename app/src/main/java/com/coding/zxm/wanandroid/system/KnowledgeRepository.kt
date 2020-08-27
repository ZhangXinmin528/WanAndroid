package com.coding.zxm.wanandroid.system

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity

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
}