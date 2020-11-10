package com.coding.zxm.wanandroid.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {


    fun getBannerData(): MutableLiveData<MutableList<BannerEntity>> {
        val liveData = MutableLiveData<MutableList<BannerEntity>>()
        viewModelScope.launch {
            val result = homeRepository.getBannerData()
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


    fun getNewsData(pageIndex: Int): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()
        viewModelScope.launch {
            val result = homeRepository.getHomeNews(pageIndex)
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


    object HomeViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(HomeRepository(RetrofitClient.getInstance(WanApp.getApplicationContext())!!)) as T
        }
    }

}