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
    var apkLink: String,
    var audit: Int,
    var author: String,//作者
    var canEdit: Boolean,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var descMd: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,//链接
    var niceDate: String,
    var niceShareDate: String,
    var origin: String,
    var originId: Int,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var realSuperChapterId: Int,
    var selfVisible: Int,
    var shareDate: Long,//分享日期
    var shareUser: String,//分享人
    var superChapterId: Int,//一级分类的第一个子类目的id,用于拼接Url
    var superChapterName: String,
    var tags: MutableList<Tag>,//标签
    var title: String,//标题
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int
)

/**
 * 资讯标签
 */
data class Tag(val name: String, val url: String)