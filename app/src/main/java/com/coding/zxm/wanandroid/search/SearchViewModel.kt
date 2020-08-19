package com.coding.zxm.wanandroid.search

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.HomeRepository
import com.coding.zxm.wanandroid.home.HomeViewModel
import com.coding.zxm.wanandroid.home.model.HotWordEntity
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 */
class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val mHotwordLiveData = MutableLiveData<MutableList<HotWordEntity>>()

    fun getHotWord(): MutableLiveData<MutableList<HotWordEntity>> {
        viewModelScope.launch {
            val result = searchRepository.getHotWord()
            if (result is NetworkResult.NetworkSuccess<MutableList<HotWordEntity>>) {
                mHotwordLiveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return mHotwordLiveData
    }

    object HomeViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(HomeRepository(RetrofitClient.INSTANCE)) as T
        }
    }
}