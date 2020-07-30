package com.coding.zxm.network.callback

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 * 网络请求状态
 */
sealed class NetworkResult<T : Any> {
    class NetworkSuccess<T : Any>(val data: T) : NetworkResult<T>()

    class NetworkError<T : Any>(val msg: String) : NetworkResult<T>()
}