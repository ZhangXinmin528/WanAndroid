package com.example.kotlinlearning.fragment

import android.view.View
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeFragment() : BaseFragment() {

    //    private lateinit var adapter: RvExampleAdapter
    private lateinit var dataList: MutableList<String>

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initParamsAndValues() {

        dataList = Array(30) { i -> "This is element $i" }.toMutableList()

//        adapter = RvExampleAdapter(dataList)
    }

    override fun initViews(rootView: View) {

        //TODO:toolbar以include的形式存在问题
//        rv_fragment_home.adapter = adapter
//        rv_fragment_home.addItemDecoration(
//            DividerItemDecoration(
//                mContext,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//        rv_fragment_home.layoutManager = LinearLayoutManager(mContext)

    }
}