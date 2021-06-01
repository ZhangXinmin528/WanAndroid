package com.coding.zxm.wanandroid.project

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.project.model.ProjectEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
class ProjectReposity(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * Request project tree.
     */
    suspend fun getProjectTree(): CommonResult<MutableList<ProjectEntity>> {
        return onCall { requestProjectTree() }
    }

    private suspend fun requestProjectTree(): CommonResult<MutableList<ProjectEntity>> {
        return executeResponse(createService(ProjectService::class.java).getProjectTree())
    }

    /**
     * 请求指定标签下的项目数据
     */
    suspend fun getProjectList(
        @IntRange(from = 0) pageIndex: Int,
        cid: Int
    ): CommonResult<NewsEntity> {
        return onCall { requestProjectList(pageIndex, cid) }
    }

    private suspend fun requestProjectList(
        @IntRange(from = 0) pageIndex: Int,
        cid: Int
    ): CommonResult<NewsEntity> {
        return executeResponse(
            createService(ProjectService::class.java).getProjectList(
                pageIndex,
                cid
            )
        )
    }

}