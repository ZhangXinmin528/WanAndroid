package com.coding.zxm.wanandroid.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class LogoutViewModel(private val logoutRepo: LogoutRepository) : ViewModel() {

    fun logout(): MutableLiveData<Int> {
        val liveData = MutableLiveData<Int>()

        viewModelScope.launch {
            val result = logoutRepo.logout()
            if (result != null) {
                if (result.errorCode == 0 && result.data == null) {
                    liveData.postValue(0)
                } else {
                    ToastUtil.showToast(result.errorMsg)

                    liveData.postValue(-1)
                }
            } else {
                ToastUtil.showUnKnownError()
                liveData.postValue(-1)
            }
        }
        return liveData
    }

    object LogoutViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LogoutViewModel(
                LogoutRepository(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }
}