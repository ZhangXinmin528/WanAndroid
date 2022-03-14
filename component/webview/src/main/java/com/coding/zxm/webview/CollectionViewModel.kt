package com.coding.zxm.webview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.core.base.BaseApp
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2022/03/14.
 * Copyright (c) 2022/3/14 . All rights reserved.
 */
class CollectionViewModel(val repo: CollectionRespository) : ViewModel() {

    /**
     * 收藏
     */
    fun collect(id: String): MutableLiveData<Int> {
        val liveData = MutableLiveData<Int>()
        viewModelScope.launch {
            val result = repo.collect(id)
            if (result is CommonResult.Success) {
                liveData.postValue(0)
            } else if (result is CommonResult.Error) {
                liveData.postValue(-1)
            }
        }
        return liveData
    }

    /**
     * 取消收藏
     */
    fun uncollect(id: String): MutableLiveData<Int> {
        val liveData = MutableLiveData<Int>()
        viewModelScope.launch {
            val result = repo.uncollect(id)
            if (result is CommonResult.Success) {
                liveData.postValue(0)
            } else if (result is CommonResult.Error) {
                liveData.postValue(-1)
            }
        }
        return liveData
    }

    object CollectionViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CollectionViewModel(
                CollectionRespository(
                    RetrofitClient.getInstance(BaseApp.getApplicationContext())!!
                )
            ) as T
        }
    }
}