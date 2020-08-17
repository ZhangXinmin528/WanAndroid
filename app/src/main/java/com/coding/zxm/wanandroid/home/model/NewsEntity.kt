package com.coding.zxm.wanandroid.home.model

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/17 . All rights reserved.
 */
data class NewsEntity(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: MutableList<NewsDetialEntity>
)

/**
 * 资讯详情
 */
data class NewsDetialEntity(
    val apkLink: String,
    val audit: Int,
    val author: String,//作者
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
    val link: String,//链接
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,//分享日期
    val shareUser: String,//分享人
    val superChapterId: Int,//一级分类的第一个子类目的id,用于拼接Url
    val superChapterName: String,
    val tags: MutableList<Tag>,//标签
    val title: String,//标题
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

/**
 * 资讯标签
 */
data class Tag(val name: String, val url: String)