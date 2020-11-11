package com.coding.zxm.wanandroid.search

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.search.model.HotWordEntity
import com.coding.zxm.wanandroid.search.model.SearchEntity

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
class SearchRepository(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * Request hot words
     */
    suspend fun getHotWord(): CommonResponse<MutableList<HotWordEntity>> {
        return creatService(SearchService::class.java).getHotWord()
    }

    /**
     * Request result match the key
     */
    suspend fun doSearch(
        @IntRange(from = 0) page: Int,
        key: String
    ): CommonResponse<SearchEntity> {
        return creatService(SearchService::class.java).doSearch(page, key)
    }

}