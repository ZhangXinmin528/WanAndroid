package com.coding.zxm.wanandroid.project

import androidx.annotation.IntRange
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.project.model.ProjectEntity
import com.coding.zxm.wanandroid.util.ToastUtil
import kotlinx.coroutines.launch

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
class ProjectViewModel(private val reposity: ProjectReposity) : ViewModel() {

    /**
     * Request project tree.
     */
    fun getProjectTree(): MutableLiveData<MutableList<ProjectEntity>> {
        val liveData = MutableLiveData<MutableList<ProjectEntity>>()
        viewModelScope.launch {
            val result = reposity.getProjectTree()
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

    fun getProjectList(
        @IntRange(from = 0) pageIndex: Int,
        cid: Int
    ): MutableLiveData<NewsEntity> {
        val liveData = MutableLiveData<NewsEntity>()

        viewModelScope.launch {
            val result = reposity.getProjectList(pageIndex, cid)
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

    object ProjectViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(
                ProjectReposity(
                    RetrofitClient.getInstance(WanApp.getApplicationContext())!!
                )
            ) as T
        }

    }

}