package com.coding.zxm.network.callback

import com.coding.zxm.network.exeption.NetworkException

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 * 网络请求状态
 */
sealed class NetworkResult<out T : Any> {
    data class NetworkSuccess<T : Any>(val data: T?) : NetworkResult<T>()

    data class NetworkError(val error: NetworkException?) : NetworkResult<Nothing>()
}