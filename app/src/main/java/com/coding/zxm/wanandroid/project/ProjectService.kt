package com.coding.zxm.wanandroid.project

import com.coding.zxm.network.common.CommonResponse
import com.coding.zxm.wanandroid.project.model.ProjectEntity
import retrofit2.http.GET

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
interface ProjectService {

    @GET("/project/tree/json")
    suspend fun getProjectTree(): CommonResponse<MutableList<ProjectEntity>>
}