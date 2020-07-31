package com.coding.zxm.network

import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.network.ecxeption.NetworkException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
class BaseRepository {

    /**
     * Do network request
     */
    suspend fun <T : Any> onRequest(call: suspend () -> NetworkResult<T>): NetworkResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.NetworkError(e)
        }
    }

    /**
     *
     */
    suspend fun <T : Any> onResponse(
        response: CommonResponse<T>,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): NetworkResult<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                NetworkResult.NetworkError(
                    NetworkException(
                        response.errorCode,
                        response.errorMsg
                    )
                )
            } else {
                successBlock?.let { it() }
                NetworkResult.NetworkSuccess(response.data)
            }
        }
    }
}