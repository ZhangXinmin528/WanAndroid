package com.coding.zxm.wanandroid.navigation.model

import com.coding.zxm.wanandroid.home.model.NewsDetialEntity

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 * 导航
 */
data class NaviEntity(
    val cid: Int,
    val name: String,
    val articles: MutableList<NewsDetialEntity>
)