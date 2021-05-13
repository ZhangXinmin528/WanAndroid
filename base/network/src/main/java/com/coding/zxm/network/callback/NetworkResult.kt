package com.coding.zxm.network.callback

import com.coding.zxm.network.common.CommonResponse

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 * 网络请求状态
 */
sealed class NetworkResult<out T : CommonResponse<T>> {
//    data class NetworkSuccess<T : CommonResponse<T>>(val data: T?) : NetworkResult<T>()
//
//    data class NetworkError<T : NetworkException>(val error: NetworkException?) :
//        NetworkResult<Nothing>()
}