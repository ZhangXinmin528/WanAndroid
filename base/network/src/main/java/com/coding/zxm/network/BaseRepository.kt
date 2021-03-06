package com.coding.zxm.network

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.network.common.CommonResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
open class BaseRepository(val client: RetrofitClient) {

    suspend fun <T : Any> onCall(call: suspend () -> CommonResult<T>): CommonResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            CommonResult.Error(e)
        }
    }


    suspend fun <T : Any> executeResponse(
        response: CommonResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): CommonResult<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                CommonResult.Error(Exception(response.errorMsg))
            } else {
                successBlock?.let { it() }
                CommonResult.Success(response.data)
            }
        }

    }

    fun <T> createService(service: Class<T>): T {
        return client.create(service)
    }
}