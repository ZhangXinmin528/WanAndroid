package com.coding.zxm.wanandroid.search

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.search.model.HotWordEntity

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

    /**
     * Request result match the key
     */
    suspend fun doSearch(
        @IntRange(from = 0) page: Int,
        key: String
    ): NetworkResult<MutableList<Any>> {
        return onRequest(call = { onSearch(page, key) })
    }

    private suspend fun onSearch(page: Int, key: String): NetworkResult<MutableList<Any>> {
        return onResponse(client.create(SearchService::class.java).doSearch(page, key))
    }
}