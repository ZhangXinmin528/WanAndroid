package com.coding.zxm.wanandroid.share

import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.collection.adapter.CollectionNewsAdapter
import com.coding.zxm.wanandroid.databinding.ActivitySharedListBinding
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.share.adapter.ShareArticleAdapter
import com.coding.zxm.webview.X5WebviewActivity

/**
 * Created by ZhangXinmin on 2022/03/28.
 * Copyright (c) 2022/3/28 . All rights reserved.
 * 已分享文章列表
 */
class SharedActivity : BaseActivity() {
    private val viewModel: ShareViewModel by viewModels { ShareViewModel.ShareViewModelFactory }
    private lateinit var listBinding: ActivitySharedListBinding

    private var mCurrentPage: Int = 0
    private val mNewsList: MutableList<NewsDetialEntity> = ArrayList()
    private lateinit var mNewsAdapter: ShareArticleAdapter

    override fun setContentLayout(): Any {
        listBinding = ActivitySharedListBinding.inflate(layoutInflater)
        return listBinding.root
    }

    override fun initParamsAndValues() {
        mNewsAdapter = ShareArticleAdapter(mNewsList)
    }

    override fun initViews() {
        setStatusBarColorLight()

        listBinding.toolbar.tvToolbarTitle.text = getString(R.string.all_my_share)
        listBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        listBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        //是否在刷新的时候禁止列表的操作
        listBinding.srSharedLayout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        listBinding.srSharedLayout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        listBinding.srSharedLayout.autoRefresh(400)

        listBinding.srSharedLayout.setOnRefreshListener {
            requestSharedData(true)
        }

        listBinding.srSharedLayout.setOnLoadMoreListener {
            requestSharedData(false)
        }

        listBinding.rvSharedList.layoutManager = LinearLayoutManager(mContext)
        listBinding.rvSharedList.adapter = mNewsAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(mContext!!, R.drawable.shape_list_horizontal_divider_gray)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        listBinding.rvSharedList.addItemDecoration(itemDecoration)

        mNewsAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as CollectionNewsAdapter).data[position]
            val jsonData = JSON.toJSONString(newsDetialEntity)
            X5WebviewActivity.loadUrl(
                this,
                newsDetialEntity.title,
                newsDetialEntity.link,
                jsonData,
                collect = newsDetialEntity.collect,
            ).observe(this, Observer {
                listBinding.srSharedLayout.autoRefresh(1000)
            })
        }

    }

    private fun requestSharedData(isRefresh: Boolean) {
        if (isRefresh) {
            mCurrentPage = 0
            if (mNewsList.isNotEmpty()) {
                mNewsList.clear()
                mNewsAdapter.notifyDataSetChanged()
            }
        }

        val newsLiveData: MutableLiveData<NewsEntity> = viewModel.getSharedArticles(mCurrentPage)

        mCurrentPage += 1

        newsLiveData.observeForever(Observer {

            if (isRefresh) {
                listBinding.srSharedLayout.finishRefresh()
            } else {
                listBinding.srSharedLayout?.finishLoadMore()
            }

            if (it == null)
                return@Observer

            val datas = it.datas
            if (datas.isNotEmpty()) {
                mNewsList.addAll(datas)
                mNewsAdapter.notifyDataSetChanged()
            }

            //没有更多数据
            if (it.over) {
                listBinding.srSharedLayout.finishLoadMoreWithNoMoreData()
            }
        })

    }
}