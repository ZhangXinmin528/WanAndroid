package com.coding.zxm.upgrade

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.core.base.BaseApp
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2021/05/14.
 * Copyright (c) 5/14/21 . All rights reserved.
 */
class UpgradeViewModel(private val repo: UpgradeRepository) : ViewModel() {

    fun checkUpgrade(): MutableLiveData<UpdateEntity> {
        val liveData = MutableLiveData<UpdateEntity>()
        viewModelScope.launch {
            val result = repo.checkUpgrade("911a59ee1bfdd702ccdd1935bde1fe30")
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object LoginViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return UpgradeViewModel(
                UpgradeRepository(
                    RetrofitClient.getInstance(BaseApp.getApplicationContext())!!
                )
            ) as T
        }
    }

}