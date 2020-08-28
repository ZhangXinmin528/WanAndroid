package com.coding.zxm.wanandroid.system.viewmodel

import android.widget.Toast
import androidx.annotation.IntRange
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.system.repository.KnowledgeRepository
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
            if (result is NetworkResult.NetworkSuccess<NewsEntity>) {
                liveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
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