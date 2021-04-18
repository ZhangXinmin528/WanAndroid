package com.coding.zxm.wanandroid.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.navigation.model.NaviEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
class NavigationViewModel(private val reposity: NavigationReposity) : ViewModel() {

    /**
     * 请求导航数据
     */
    fun getNavigationData(): MutableLiveData<MutableList<NaviEntity>> {
        val liveData = MutableLiveData<MutableList<NaviEntity>>()

        viewModelScope.launch {
            val result = reposity.getNavigationData()
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object NavigationViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NavigationViewModel(
                NavigationReposity(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }
}