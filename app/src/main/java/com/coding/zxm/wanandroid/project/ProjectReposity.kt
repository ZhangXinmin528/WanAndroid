package com.coding.zxm.wanandroid.project

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.project.model.ProjectEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
class ProjectReposity(private val client: RetrofitClient) : BaseRepository() {

    /**
     * Request project tree.
     */
    suspend fun getProjectTree(): NetworkResult<MutableList<ProjectEntity>> {
        return onRequest(call = { requestProjectTree() })
    }

    private suspend fun requestProjectTree(): NetworkResult<MutableList<ProjectEntity>> {
        return onResponse(client.create(ProjectService::class.java).getProjectTree())
    }
}