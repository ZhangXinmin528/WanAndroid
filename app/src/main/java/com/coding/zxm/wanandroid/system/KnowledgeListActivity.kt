package com.coding.zxm.wanandroid.system

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.app.WanApp.Companion.context
import com.coding.zxm.wanandroid.home.adapter.HomeNewsAdapter
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.system.viewmodel.KnowledgeListViewModel
import com.coding.zxm.webview.X5WebviewActivity
import kotlinx.android.synthetic.main.activity_knowledge_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*

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

    override fun setLayoutId(): Int {
        return R.layout.activity_knowledge_list
    }

    override fun initParamsAndValues() {
        if (intent != null) {
            mTitle = intent.getStringExtra(PARAMAS_TITLE)
            mCid = intent.getIntExtra(PARAMAS_CID, 0)
        }

        mNewsAdapter = HomeNewsAdapter(mArticleList)

    }

    override fun initViews() {
        setSupportActionBar(toolbar_wan)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = if (TextUtils.isEmpty(mTitle)) "知识体系专栏" else "${mTitle}专栏"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        //是否在刷新的时候禁止列表的操作
        sr_list_layout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        sr_list_layout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        sr_list_layout.autoRefresh(400)

        sr_list_layout.setOnRefreshListener {
            getArticles(true)
        }

        sr_list_layout.setOnLoadMoreListener {
            getArticles(false)
        }

        rv_fragment_list.layoutManager = LinearLayoutManager(mContext)
        rv_fragment_list.adapter = mNewsAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(context, R.drawable.icon_search_divider)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        rv_fragment_list.addItemDecoration(itemDecoration)

        mNewsAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as HomeNewsAdapter).data[position]

            X5WebviewActivity.loadUrl(mContext!!, newsDetialEntity.title, newsDetialEntity.link)
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
                    sr_list_layout.finishRefresh()
                } else {
                    sr_list_layout.finishLoadMore()
                }

                if (it == null) return@Observer

                val datas = it.datas
                if (datas.isNotEmpty()) {
                    mArticleList.addAll(datas)
                    mNewsAdapter.notifyDataSetChanged()
                }

                //没有更多数据
                if (it.over) {
                    sr_list_layout.finishLoadMoreWithNoMoreData()
                }
            })
        }

    }
}