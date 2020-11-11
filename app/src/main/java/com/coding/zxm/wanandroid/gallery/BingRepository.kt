package com.coding.zxm.wanandroid.gallery

import androidx.annotation.IntRange
import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.coding.zxm.wanandroid.gallery.model.BingResponse

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/11 . All rights reserved.
 */
class BingRepository(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * 获取Bing壁纸数据
     */
    suspend fun getBingPicList(
        @IntRange(from = 0) pageIndex: Int,
        @IntRange(from = 1) pageSize: Int
    ): BingResponse<MutableList<BingImageEntity>> {
        return creatService(BingService::class.java).getBingPicList(pageIndex, pageSize)
    }
}