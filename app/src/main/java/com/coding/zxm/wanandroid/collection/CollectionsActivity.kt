package com.coding.zxm.wanandroid.collection

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
import com.coding.zxm.wanandroid.databinding.ActivityCollectionsListBinding
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.webview.OnCollectionChangedListener
import com.coding.zxm.webview.X5WebviewActivity

/**
 * Created by ZhangXinmin on 2022/03/08.
 * Copyright (c) 2022/3/8 . All rights reserved.
 * 我的收藏页面
 */
class CollectionsActivity : BaseActivity() {

    private val viewModel: CollectionViewModel by viewModels { CollectionViewModel.CollectionViewModelFactory }
    private lateinit var listBinding: ActivityCollectionsListBinding

    private var mCurrentPage: Int = 0
    private val mNewsList: MutableList<NewsDetialEntity> = ArrayList()
    private lateinit var mNewsAdapter: CollectionNewsAdapter

    override fun setContentLayout(): Any {
        listBinding = ActivityCollectionsListBinding.inflate(layoutInflater)
        return listBinding.root
    }

    override fun initParamsAndValues() {
        mNewsAdapter = CollectionNewsAdapter(mNewsList)

    }

    override fun initViews() {
        setStatusBarColorLight()

        listBinding.toolbar.tvToolbarTitle.text = getString(R.string.all_my_collections)
        listBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        listBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        //是否在刷新的时候禁止列表的操作
        listBinding.srCollectionsLayout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        listBinding.srCollectionsLayout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        listBinding.srCollectionsLayout.autoRefresh(400)

        listBinding.srCollectionsLayout.setOnRefreshListener {
            requestNewsData(true)
        }

        listBinding.srCollectionsLayout.setOnLoadMoreListener {
            requestNewsData(false)
        }

        listBinding.rvCollectionList.layoutManager = LinearLayoutManager(mContext)
        listBinding.rvCollectionList.adapter = mNewsAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(mContext!!, R.drawable.shape_list_horizontal_divider_gray)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        listBinding.rvCollectionList.addItemDecoration(itemDecoration)

        mNewsAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as CollectionNewsAdapter).data[position]
            val jsonData = JSON.toJSONString(newsDetialEntity)
            X5WebviewActivity.loadUrl(
                this,
                newsDetialEntity.title,
                newsDetialEntity.link,
                jsonData,
                collect = true,
                callback = object :OnCollectionChangedListener{
                    override fun collectionChanged() {
                        listBinding.srCollectionsLayout.autoRefresh(400)
                    }
                }
            )
        }

    }

    private fun requestNewsData(isRefresh: Boolean) {
        if (isRefresh) {
            mCurrentPage = 0
            if (mNewsList.isNotEmpty()) {
                mNewsList.clear()
                mNewsAdapter.notifyDataSetChanged()
            }
        }

        val newsLiveData: MutableLiveData<NewsEntity> = viewModel.getCollectionsList(mCurrentPage)

        mCurrentPage += 1

        newsLiveData.observeForever(Observer {

            if (isRefresh) {
                listBinding.srCollectionsLayout.finishRefresh()
            } else {
                listBinding.srCollectionsLayout?.finishLoadMore()
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
                listBinding.srCollectionsLayout.finishLoadMoreWithNoMoreData()
            }
        })

    }
}