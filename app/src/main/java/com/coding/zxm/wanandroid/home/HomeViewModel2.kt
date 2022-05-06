package com.coding.zxm.wanandroid.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.core.base.BaseViewModel
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/12.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeViewModel2(private val homeRepository: HomeRepository,val app:Application) : BaseViewModel(app) {

    fun getBannerData(): MutableLiveData<MutableList<BannerEntity>> {
        val liveData = MutableLiveData<MutableList<BannerEntity>>()
        viewModelScope.launch {
            val result = homeRepository.getBannerData()
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }


    fun getNewsData(pageIndex: Int): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()
        viewModelScope.launch {
            val result = homeRepository.getHomeNews(pageIndex)
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

}