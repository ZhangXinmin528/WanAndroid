package com.coding.zxm.wanandroid.system

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivityKnowledgeListBinding
import com.coding.zxm.wanandroid.home.adapter.HomeNewsAdapter
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.system.viewmodel.KnowledgeListViewModel
import com.coding.zxm.webview.OnCollectionChangedListener
import com.coding.zxm.webview.X5WebviewActivity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/28 . All rights reserved.
 */
class KnowledgeListActivity : BaseActivity() {

    private val mListViewModel: KnowledgeListViewModel by viewModels { KnowledgeListViewModel.KnowledgeListViewModel }

    companion object {
        private const val PARAMAS_TITLE: String = "params_title"
        private const val PARAMAS_CID: String = "params_cid"

        fun startActicles(context: Context, title: String, cid: Int) {
            val intent = Intent(context, KnowledgeListActivity::class.java)
            intent.putExtra(PARAMAS_TITLE, title)
            intent.putExtra(PARAMAS_CID, cid)
            context.startActivity(intent)
        }
    }

    private var mTitle: String? = ""
    private var mCid: Int? = 0
    private var mCurrentPage: Int = 0
    private val mArticleList: MutableList<NewsDetialEntity> = ArrayList()
    private lateinit var mNewsAdapter: HomeNewsAdapter

    private lateinit var listBinding: ActivityKnowledgeListBinding

    override fun setContentLayout(): Any {
        listBinding = ActivityKnowledgeListBinding.inflate(layoutInflater)
        return listBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()
        if (intent != null) {
            mTitle = intent.getStringExtra(PARAMAS_TITLE)
            mCid = intent.getIntExtra(PARAMAS_CID, 0)
        }

        mNewsAdapter = HomeNewsAdapter(mArticleList)

    }

    override fun initViews() {

        listBinding.toolbar.tvToolbarTitle.text =
            if (TextUtils.isEmpty(mTitle)) getString(R.string.all_system_column_title) else getString(
                R.string.all_target_column_title, mTitle
            )
        listBinding.toolbar.ivToolbarBack.setOnClickListener { finish() }

        //是否在刷新的时候禁止列表的操作
        listBinding.srListLayout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        listBinding.srListLayout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        listBinding.srListLayout.autoRefresh(400)

        listBinding.srListLayout.setOnRefreshListener {
            getArticles(true)
        }

        listBinding.srListLayout.setOnLoadMoreListener {
            getArticles(false)
        }

        listBinding.rvFragmentList.layoutManager = LinearLayoutManager(mContext)
        listBinding.rvFragmentList.adapter = mNewsAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(mContext!!, R.drawable.shape_list_horizontal_divider_gray)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        listBinding.rvFragmentList.addItemDecoration(itemDecoration)

        mNewsAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as HomeNewsAdapter).data[position]
            val jsonData = JSON.toJSONString(newsDetialEntity)
            X5WebviewActivity.loadUrl(
                mContext!!,
                newsDetialEntity.title,
                newsDetialEntity.link,
                jsonData,
                collect = newsDetialEntity.collect,
                callback = object :
                    OnCollectionChangedListener {
                    override fun collectionChanged() {
                        listBinding.srListLayout.autoRefresh(400)
                    }
                }
            )
        }

    }

    private fun getArticles(isRefresh: Boolean) {
        if (isRefresh) {
            mCurrentPage = 0
            if (mArticleList.isNotEmpty()) {
                mArticleList.clear()
                mNewsAdapter.notifyDataSetChanged()
            }
        }
        mCid?.let {
            val liveData = mListViewModel.getKnowledgeArticles(mCurrentPage, it)
            mCurrentPage += 1

            liveData.observe(this, Observer {
                if (isRefresh) {
                    listBinding.srListLayout.finishRefresh()
                } else {
                    listBinding.srListLayout.finishLoadMore()
                }

                if (it == null) return@Observer

                val datas = it.datas
                if (datas.isNotEmpty()) {
                    mArticleList.addAll(datas)
                    mNewsAdapter.notifyDataSetChanged()
                }

                //没有更多数据
                if (it.over) {
                    listBinding.srListLayout.finishLoadMoreWithNoMoreData()
                }
            })
        }

    }
}