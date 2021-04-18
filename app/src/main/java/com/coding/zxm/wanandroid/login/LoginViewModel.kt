package com.coding.zxm.wanandroid.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 */

open class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {


    /**
     * User login
     */
    fun login(userName: String, passWord: String): MutableLiveData<UserEntity> {

        val liveData = MutableLiveData<UserEntity>()

        viewModelScope.launch {
            val result = loginRepo.login(userName, passWord)
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    /**
     * User register
     */
    fun register(
        userName: String,
        passWord: String,
        repassword: String
    ): MutableLiveData<UserEntity> {
        val liveData = MutableLiveData<UserEntity>()
        viewModelScope.launch {
            val result = loginRepo.register(userName, passWord, repassword)
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object LoginViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                LoginRepository(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }
}