package com.coding.zxm.wanandroid.collection

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
 * Created by ZhangXinmin on 2022/03/08.
 * Copyright (c) 2022/3/8 . All rights reserved.
 */
class CollectionViewModel(private val repository: CollectionRepository) : ViewModel() {

    fun getCollectionsList(pageIndex: Int): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()
        viewModelScope.launch {
            val result = repository.getCollectionsList(pageIndex)
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object CollectionViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CollectionViewModel(CollectionRepository(RetrofitClient.getInstance(WanApp.getApplicationContext())!!)) as T
        }
    }
}