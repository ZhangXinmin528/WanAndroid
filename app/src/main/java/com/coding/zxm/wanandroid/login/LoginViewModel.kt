package com.coding.zxm.wanandroid.login

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
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 */

open class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {

    private val loginLiveData = MutableLiveData<UserEntity>()

    /**
     * User login
     */
    fun login(userName: String, passWord: String): MutableLiveData<UserEntity> {
        viewModelScope.launch {
            val result = loginRepo.login(userName, passWord)
            if (result is NetworkResult.NetworkSuccess<UserEntity>) {
                loginLiveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return loginLiveData
    }

    /**
     * User register
     */
    fun register(userName: String, passWord: String, repassword: String) {
        viewModelScope.launch {
            val result = loginRepo.register(userName, passWord, repassword)
            if (result is NetworkResult.NetworkSuccess<UserEntity>) {
                loginLiveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    object LoginViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(LoginRepository(RetrofitClient.INSTANCE)) as T
        }

    }
}