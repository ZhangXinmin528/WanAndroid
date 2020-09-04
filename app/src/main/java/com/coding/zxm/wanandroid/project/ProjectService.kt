package com.coding.zxm.wanandroid.project

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.project.model.ProjectEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
interface ProjectService {

    @GET("/project/tree/json")
    suspend fun getProjectTree(): CommonResponse<MutableList<ProjectEntity>>

    /**
     * 获取指定标签下的项目数据
     */
    @GET("/project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): CommonResponse<NewsEntity>
}