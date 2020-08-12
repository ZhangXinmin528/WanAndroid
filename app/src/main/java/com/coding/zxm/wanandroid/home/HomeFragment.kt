package com.coding.zxm.wanandroid.home

import android.view.View
import androidx.fragment.app.viewModels
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeFragment() : BaseFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels { HomeViewModel.HomeViewModelFactory }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {

    }
}