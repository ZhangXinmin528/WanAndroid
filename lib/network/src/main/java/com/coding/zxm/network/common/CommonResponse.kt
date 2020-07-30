package com.coding.zxm.network.common

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/30 . All rights reserved.
 */
data class CommonResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)