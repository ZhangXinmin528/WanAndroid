package com.coding.zxm.wanandroid.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/11 . All rights reserved.
 */
class BingViewModel(var repo: BingRepository) : ViewModel() {

    /**
     *获取bing壁纸图片
     */
    fun getBingPicList(): MutableLiveData<MutableList<BingImageEntity>> {
        val liveData = MutableLiveData<MutableList<BingImageEntity>>()

        viewModelScope.launch {
            val result = repo.getBingPicList()
            if (result is CommonResult.Success) {
                liveData.postValue(result.data)
            } else if (result is CommonResult.Error) {
                ToastUtil.showToast(result.exception.message)
                liveData.postValue(null)
            }
        }
        return liveData
    }

    object BingViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return BingViewModel(BingRepository(RetrofitClient.getInstance(WanApp.getApplicationContext())!!)) as T
        }
    }
}