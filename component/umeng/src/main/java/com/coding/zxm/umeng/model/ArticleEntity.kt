package com.coding.zxm.umeng.model

/**
 * Created by ZhangXinmin on 2022/03/09.
 * Copyright (c) 2022/3/9 . All rights reserved.
 * 分享文章的关键内容
 */
class ArticleEntity {
    var title: String? = ""
    var niceDate: String? = ""
    var link: String? = ""

    //文章原ID
    var originId: String = "0"

    //文章id
    var id: String = ""

    //搜藏状态
    var collect: Boolean = false
}