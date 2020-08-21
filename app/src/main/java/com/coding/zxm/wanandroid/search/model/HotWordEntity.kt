package com.coding.zxm.wanandroid.search.model

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.coding.zxm.wanandroid.search.adapter.HotWordAdapter

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 * 用于热词的搜索
 */
data class HotWordEntity(
    var id: Int,
    var link: String? = "",
    var name: String? = "",
    var order: Int? = 0,
    var visible: Int? = 0
)