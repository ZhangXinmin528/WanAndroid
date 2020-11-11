package com.coding.zxm.wanandroid.system.viewmodel

import androidx.annotation.IntRange
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.system.repository.KnowledgeRepository
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
class KnowledgeListViewModel(private val repository: KnowledgeRepository) : ViewModel() {

    fun getKnowledgeArticles(
        @IntRange(from = 0) pageIndex: Int,
        id: Int
    ): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()

        viewModelScope.launch {
            val result = repository.getKnowledgeArticles(pageIndex, id)
            if (result != null) {
                if (result.errorCode == 0) {
                    liveData.postValue(result.data)
                } else {
                    ToastUtil.showToast(result.errorMsg)

                    liveData.postValue(null)
                }
            } else {
                ToastUtil.showUnKnownError()
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object KnowledgeListViewModel : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return KnowledgeListViewModel(
                KnowledgeRepository(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }
}