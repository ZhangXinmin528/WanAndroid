package com.coding.zxm.wanandroid.search.model

import com.coding.zxm.wanandroid.home.model.NewsDetialEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/21 . All rights reserved.
 * 搜索结果
 */
class SearchEntity(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<NewsDetialEntity>
)