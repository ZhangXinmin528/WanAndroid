package com.coding.zxm.wanandroid.navigation

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.navigation.model.NaviEntity
import com.coding.zxm.webview.X5WebviewActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
class NavigationActivity : BaseActivity() {

    private val mNaviViewModel: NavigationViewModel by viewModels { NavigationViewModel.NavigationViewModelFactory }

    private val mDataList: MutableList<NaviEntity> = ArrayList()
    private lateinit var mNaviAdapter: NavigationAdapter

    override fun setLayoutId(): Int {
        return R.layout.activity_navigation
    }

    override fun initParamsAndValues() {
        mNaviAdapter = NavigationAdapter(mDataList)

    }

    override fun initViews() {
        setSupportActionBar(toolbar_wan)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = "导航"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        //是否在刷新的时候禁止列表的操作
        sr_navi_layout.setDisableContentWhenRefresh(true)
        sr_navi_layout.setEnableLoadMore(false)

        //延迟400毫秒后自动刷新
        sr_navi_layout.autoRefresh(400)

        sr_navi_layout.setOnRefreshListener {
            getNavigationData()
        }

        rv_navi.adapter = mNaviAdapter
        rv_navi.layoutManager = LinearLayoutManager(mContext)
        val itemDecoration =
            DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)

        val divider =
            ContextCompat.getDrawable(mContext!!, R.drawable.shape_gray_horizontal_divider)
        itemDecoration.setDrawable(divider!!)
        rv_navi.addItemDecoration(itemDecoration)

        mNaviAdapter.setNaviTagClicklistener(object : NavigationAdapter.OnNaviTagClickListener {
            override fun onTagItemClick(view: View, newsEntity: NewsDetialEntity) {

                X5WebviewActivity.loadUrl(mContext!!, newsEntity.title, newsEntity.link)
            }

        })
    }

    private fun getNavigationData() {
        val liveData = mNaviViewModel.getNavigationData()

        liveData.observe(this, Observer {
            sr_navi_layout.finishRefresh()
            if (it != null && it.isNotEmpty()) {
                if (mDataList.isNotEmpty()) {
                    mDataList.clear()
                }
                mDataList.addAll(it)
                mNaviAdapter.notifyDataSetChanged()
            }
        })
    }
}