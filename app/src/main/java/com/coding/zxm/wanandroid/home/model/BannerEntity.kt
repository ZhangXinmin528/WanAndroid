package com.coding.zxm.wanandroid.home.model

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
data class BannerEntity(
    var desc: String? = null,
    var id: Int,
    var imagePath: String,
    var isVisible: Int? = 0,
    var order: Int? = 0,
    var title: String? = null,
    var type: Int? = 0,
    var url: String
)