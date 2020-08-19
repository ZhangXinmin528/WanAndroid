package com.coding.zxm.wanandroid.home

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.HotWordEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val mBannerLiveData = MutableLiveData<MutableList<BannerEntity>>()

    fun getBannerData(): MutableLiveData<MutableList<BannerEntity>> {
        viewModelScope.launch {
            val result = homeRepository.getBannerData()
            if (result is NetworkResult.NetworkSuccess<MutableList<BannerEntity>>) {
                mBannerLiveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return mBannerLiveData
    }


    private val mNewsLiveData = MutableLiveData<NewsEntity>()

    fun getNewsData(pageIndex: Int): MutableLiveData<NewsEntity> {

        viewModelScope.launch {
            val result = homeRepository.getHomeNews(pageIndex)
            if (result is NetworkResult.NetworkSuccess<NewsEntity>) {
                mNewsLiveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return mNewsLiveData
    }


    object HomeViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(HomeRepository(RetrofitClient.INSTANCE)) as T
        }
    }

}