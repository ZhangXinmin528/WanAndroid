package com.coding.zxm.wanandroid.share

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.share.model.ShareEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by ZhangXinmin on 2022/03/14.
 * Copyright (c) 2022/3/14 . All rights reserved.
 */
interface ShareService {

    @GET("/user/lg/private_articles/{pageIndex}/json")
    suspend fun getShareArticleList(@Path("pageIndex") pageIndex: Int): CommonResponse<ShareEntity>

    @POST("/lg/user_article/delete/{id}/json")
    suspend fun deleteSharedArticle(@Path("id") id: Int): CommonResponse<Any>
}