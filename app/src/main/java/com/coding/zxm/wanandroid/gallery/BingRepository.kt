package com.coding.zxm.wanandroid.gallery

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.coding.zxm.wanandroid.gallery.model.BingResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/11 . All rights reserved.
 */
class BingRepository(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * 获取Bing壁纸数据
     */
    suspend fun getBingPicList(): CommonResult<MutableList<BingImageEntity>> {
        return onCall { requestBingPicList() }
    }

    private suspend fun requestBingPicList(): CommonResult<MutableList<BingImageEntity>> {
        return excuteBingResponse(creatService(BingService::class.java).getBingPicList())
    }

    private suspend fun <T : Any> excuteBingResponse(
        response: BingResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): CommonResult<T> {
        return coroutineScope {
            if (response == null) {
                errorBlock?.let { it() }
                CommonResult.Error(Exception("网络请求异常"))
            } else {
                successBlock?.let { it() }
                CommonResult.Success(response.images)
            }
        }

    }

}