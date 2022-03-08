package com.coding.zxm.wanandroid.collection

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.home.model.NewsEntity

/**
 * Created by ZhangXinmin on 2022/03/08.
 * Copyright (c) 2022/3/8 . All rights reserved.
 */
class CollectionRepository(client: RetrofitClient) : BaseRepository(client) {

    /**
     * 获取收藏文章列表
     */
    suspend fun getCollectionsList(@IntRange(from = 0) pageIndex: Int): CommonResult<NewsEntity> {
        return onCall { requestCollectionsList(pageIndex) }
    }

    private suspend fun requestCollectionsList(@IntRange(from = 0) pageIndex: Int): CommonResult<NewsEntity> {
        return executeResponse(
            createService(CollectionService::class.java).getCollectionsList(
                pageIndex
            )
        )
    }
}