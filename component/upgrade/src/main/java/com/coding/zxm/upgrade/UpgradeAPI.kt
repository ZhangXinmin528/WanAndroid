package com.coding.zxm.upgrade

import com.coding.zxm.upgrade.entity.UpdateEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by ZhangXinmin on 2021/05/14.
 * Copyright (c) 5/14/21 . All rights reserved.
 */
interface UpgradeAPI {

    /**
     * 检查版本更新
     */
    @GET("latest/5fd2db28b2eb462419f80455")
    fun checkUpdate(@Query("api_token") token: String): Call<UpdateEntity>

    /**
     * 下载更新APK
     */
    @Streaming
    @GET
    fun downloadApk(@Url downloadUrl: String): Call<ResponseBody>
}