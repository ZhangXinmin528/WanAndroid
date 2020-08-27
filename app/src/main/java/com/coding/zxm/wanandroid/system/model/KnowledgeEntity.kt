package com.coding.zxm.wanandroid.system.model

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
data class KnowledgeEntity(
    val id: Int,//查看文章时使用
    val courseId: Int,
    val name: String = "",//名称
    val order: Int,
    val children: MutableList<KnowledgeEntity>,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)

data class NaviKnowledgeEntity(
    var naviItem: KnowledgeEntity,
    var selected: Boolean = false
)