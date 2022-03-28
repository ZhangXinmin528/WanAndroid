package com.coding.zxm.wanandroid.share

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.share.model.ShareEntity

/**
 * Created by ZhangXinmin on 2022/03/28.
 * Copyright (c) 2022/3/28 . All rights reserved.
 */
class ShareRepository(client: RetrofitClient) : BaseRepository(client) {

    /**
     * 获取已分享文章列表
     */
    suspend fun getShareArticleList(@IntRange(from = 0) pageIndex: Int): CommonResult<ShareEntity> {
        return onCall { requestShareArticleList(pageIndex) }
    }

    private suspend fun requestShareArticleList(@IntRange(from = 0) pageIndex: Int): CommonResult<ShareEntity> {
        return executeResponse(
            client.create(ShareService::class.java).getShareArticleList(pageIndex)
        )
    }

    /**
     * 删除已分享文章
     */
    suspend fun deleteSharedArticle(@IntRange(from = 0) id: Int): CommonResult<Any> {
        return onCall { deleteSharedArticleById(id) }
    }

    private suspend fun deleteSharedArticleById(@IntRange(from = 0) id: Int): CommonResult<Any> {
        return executeResponse(
            client.create(ShareService::class.java).deleteSharedArticle(id)
        )
    }
}