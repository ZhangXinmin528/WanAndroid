package com.coding.zxm.wanandroid.search.model

import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.Tag

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/21 . All rights reserved.
 * 搜索结果
 */
data class SearchEntity(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<SearchDetialEntity>
)

/**
 * Search result entity.
 */
data class SearchDetialEntity(
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: MutableList<Tag>,//标签
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)