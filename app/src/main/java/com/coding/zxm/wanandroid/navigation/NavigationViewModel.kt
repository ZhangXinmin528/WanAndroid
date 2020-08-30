package com.coding.zxm.wanandroid.navigation

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.navigation.model.NaviEntity
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
            if (result is NetworkResult.NetworkSuccess<MutableList<NaviEntity>>) {
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