package com.coding.zxm.webview

import androidx.annotation.NonNull
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult

/**
 * Created by ZhangXinmin on 2022/03/14.
 * Copyright (c) 2022/3/14 . All rights reserved.
 */
class CollectionRespository(client: RetrofitClient) : BaseRepository(client) {
    /**
     * Do collect an article by id.
     */
    suspend fun collect(id: String): CommonResult<Any> {
        return onCall { onCollect(id) }
    }

    suspend fun onCollect(@NonNull id: String): CommonResult<Any> {
        return executeResponse(createService(CollectionService::class.java).doCollect(id))
    }

    /**
     * Cancle collect an article by id.
     */
    suspend fun uncollect(id: String): CommonResult<Any> {
        return onCall { onUncollect(id) }
    }

    suspend fun onUncollect(id: String): CommonResult<Any> {
        return executeResponse(createService(CollectionService::class.java).doUncollect(id))
    }
}