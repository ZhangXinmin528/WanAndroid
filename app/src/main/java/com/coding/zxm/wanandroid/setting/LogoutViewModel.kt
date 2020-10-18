package com.coding.zxm.wanandroid.setting

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/10/18.
 * Copyright (c) 2020 . All rights reserved.
 */
class LogoutViewModel(private val logoutRepo: LogoutRepository) : ViewModel() {

    fun logout(): MutableLiveData<Int> {
        val logoutLivedata = MutableLiveData<Int>()

        viewModelScope.launch {
            val result = logoutRepo.logout()
            if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
                logoutLivedata.postValue(-1)
            } else if (result is NetworkResult.NetworkSuccess<Any>) {
                if (result.data == null) {
                    logoutLivedata.postValue(0)
                } else {
                    logoutLivedata.postValue(-1)
                }
            }
        }
        return logoutLivedata
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