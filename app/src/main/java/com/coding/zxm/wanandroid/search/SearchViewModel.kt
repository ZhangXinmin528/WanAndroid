package com.coding.zxm.wanandroid.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.search.model.HotWordEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    fun getHotWord(): MutableLiveData<MutableList<HotWordEntity>> {
        val liveData = MutableLiveData<MutableList<HotWordEntity>>()
        viewModelScope.launch {
            val result = searchRepository.getHotWord()
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    fun doSearch(page: Int, key: String): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()

        viewModelScope.launch {
            val result = searchRepository.doSearch(page, key)
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object SearchViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(
                SearchRepository(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }
    }
}