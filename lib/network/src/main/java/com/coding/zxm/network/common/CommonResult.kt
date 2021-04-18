package com.coding.zxm.network.common

/**
 * Created by ZhangXinmin on 2021/04/18.
 * Copyright (c) 4/18/21 . All rights reserved.
 */
sealed class CommonResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : CommonResult<T>()
    data class Error(val exception: Exception) : CommonResult<Nothing>()
}