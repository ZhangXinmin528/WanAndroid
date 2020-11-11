package com.coding.zxm.wanandroid.gallery.model

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/11 . All rights reserved.
 * Bing图片接口响应
 */
data class BingResponse<out T>(val images: T)