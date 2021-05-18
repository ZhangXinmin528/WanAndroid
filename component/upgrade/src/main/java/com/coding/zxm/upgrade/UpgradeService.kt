package com.coding.zxm.upgrade

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by ZhangXinmin on 2021/05/14.
 * Copyright (c) 5/14/21 . All rights reserved.
 */
interface UpgradeService {

    /**
     * 检查版本更新
     */
    @GET
    fun checkUpdate(): Call<UpdateEntity>

    /**
     * 下载更新APK
     */
    @GET
    fun downloadApk(@Url downloadUrl: String): Call<ResponseBody>
}