package com.coding.zxm.wanandroid.system.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.system.repository.KnowledgeRepository
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
class KnowledgeViewModel(private val repository: KnowledgeRepository) : ViewModel() {

    fun getKnowledgeTree(): MutableLiveData<MutableList<KnowledgeEntity>> {
        val liveData = MutableLiveData<MutableList<KnowledgeEntity>>()

        viewModelScope.launch {
            val result = repository.getKnowledgeTree()
            if (result is NetworkResult.NetworkSuccess<MutableList<KnowledgeEntity>>) {
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

    object KnowledgeViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return KnowledgeViewModel(
                KnowledgeRepository(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }
}