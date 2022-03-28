package com.coding.zxm.wanandroid.share

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2022/03/28.
 * Copyright (c) 2022/3/28 . All rights reserved.
 */
class ShareViewModel(private val repo: ShareRepository) : ViewModel() {

    /**
     * 获取已经分享的文章列表
     */
    fun getSharedArticles(pageIndex: Int): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()
        viewModelScope.launch {
            val result = repo.getShareArticleList(pageIndex)
            if (result is CommonResult.Success) {
                liveData.postValue(result.data?.shareArticles)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }


    object ShareViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ShareViewModel(ShareRepository(RetrofitClient.getInstance(WanApp.getApplicationContext())!!)) as T
        }
    }
}