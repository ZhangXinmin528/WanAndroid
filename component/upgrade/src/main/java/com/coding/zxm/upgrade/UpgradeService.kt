package com.coding.zxm.upgrade

import com.coding.zxm.network.APIConstants
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by ZhangXinmin on 2021/05/14.
 * Copyright (c) 5/14/21 . All rights reserved.
 */
interface UpgradeService {

    /**
     * 检查版本更新（携程）
     */
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + APIConstants.DOMAIN_UPGRADE)
    @GET("/latest/5fd2db28b2eb462419f80455")
    suspend fun checkUpdate(@Query("api_token") token: String): UpdateEntity

    /**
     * 检查版本更新(一般使用)
     */
    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + APIConstants.DOMAIN_UPGRADE)
    @GET("/latest/5fd2db28b2eb462419f80455")
    fun checkUpdate2(@Query("api_token") token: String): Call<UpdateEntity>
}