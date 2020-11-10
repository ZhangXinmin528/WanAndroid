package com.coding.zxm.network

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
open class BaseRepository(val client: RetrofitClient) {

    fun <T> creatService(service: Class<T>): T {
        return client.create(service)
    }
}