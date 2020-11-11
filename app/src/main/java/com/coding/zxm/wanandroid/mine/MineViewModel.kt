package com.coding.zxm.wanandroid.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.mine.model.UserDetialEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
class MineViewModel(private val mineRepository: MineRepository) : ViewModel() {

    fun getUserInfo(): MutableLiveData<UserDetialEntity> {
        val liveData = MutableLiveData<UserDetialEntity>()
        viewModelScope.launch {
            val result = mineRepository.getUserInfo()
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

    object MineViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MineViewModel(
                MineRepository(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }
}