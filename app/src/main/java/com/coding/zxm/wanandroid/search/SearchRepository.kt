package com.coding.zxm.wanandroid.search

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.home.model.HotWordEntity

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
class SearchRepository(private val client: RetrofitClient) : BaseRepository() {

    /**
     * Request hot words
     */
    suspend fun getHotWord(): NetworkResult<MutableList<HotWordEntity>> {
        return onRequest(call = { requestHotWord() })
    }

    private suspend fun requestHotWord(): NetworkResult<MutableList<HotWordEntity>> {
        return onResponse(client.create(SearchService::class.java).getHotWord())
    }
}