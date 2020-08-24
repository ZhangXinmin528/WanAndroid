package com.coding.zxm.wanandroid.mine

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.mine.model.UserDetialEntity
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
class MineViewModel(private val mineRepository: MineRepository) : ViewModel() {

     fun getUserInfo(): MutableLiveData<UserDetialEntity> {
        val userLiveData = MutableLiveData<UserDetialEntity>()
        viewModelScope.launch {
            val result = mineRepository.getUserInfo()
            if (result is NetworkResult.NetworkSuccess<UserDetialEntity>) {
                userLiveData.postValue(result.data)
            } else if (result is NetworkResult.NetworkError) {
                Toast.makeText(
                    WanApp.getApplicationContext(),
                    result.error?.errorMsg,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        return userLiveData
    }

    object MineViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MineViewModel(
                MineRepository(
                    RetrofitClient.INSTANCE
                )
            ) as T
        }

    }
}