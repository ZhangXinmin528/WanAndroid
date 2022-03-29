package com.coding.zxm.wanandroid.navigation

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivityNavigationBinding
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.navigation.model.NaviEntity
import com.coding.zxm.webview.OnCollectionChangedListener
import com.coding.zxm.webview.X5WebviewActivity

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
class NavigationActivity : BaseActivity() {

    private val mNaviViewModel: NavigationViewModel by viewModels { NavigationViewModel.NavigationViewModelFactory }

    private val mDataList: MutableList<NaviEntity> = ArrayList()
    private lateinit var mNaviAdapter: NavigationAdapter
    private lateinit var navigationBinding: ActivityNavigationBinding

    override fun setContentLayout(): Any {
        navigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
        return navigationBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()
        mNaviAdapter = NavigationAdapter(mDataList)

    }

    override fun initViews() {

        navigationBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        navigationBinding.toolbar.tvToolbarTitle.text = getString(R.string.all_navigation_title)


        //是否在刷新的时候禁止列表的操作
        navigationBinding.srNaviLayout.setDisableContentWhenRefresh(true)
        navigationBinding.srNaviLayout.setEnableLoadMore(false)

        //延迟400毫秒后自动刷新
        navigationBinding.srNaviLayout.autoRefresh(400)

        navigationBinding.srNaviLayout.setOnRefreshListener {
            getNavigationData()
        }

        navigationBinding.rvNavi.adapter = mNaviAdapter
        navigationBinding.rvNavi.layoutManager = LinearLayoutManager(mContext)
        val itemDecoration =
            DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)

        val divider =
            ContextCompat.getDrawable(mContext!!, R.drawable.shape_gray_horizontal_divider_8dp)
        itemDecoration.setDrawable(divider!!)
        navigationBinding.rvNavi.addItemDecoration(itemDecoration)

        mNaviAdapter.setNaviTagClicklistener(object : NavigationAdapter.OnNaviTagClickListener {
            override fun onTagItemClick(view: View, newsEntity: NewsDetialEntity) {
                val jsonData = JSON.toJSONString(newsEntity)
                X5WebviewActivity.loadUrl(
                    mContext!!,
                    newsEntity.title,
                    newsEntity.link,
                    jsonData,
                    collect = newsEntity.collect,
                )
            }

        })
    }

    private fun getNavigationData() {
        val liveData = mNaviViewModel.getNavigationData()

        liveData.observe(this, Observer {
            navigationBinding.srNaviLayout.finishRefresh()
            if (it == null)
                return@Observer

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