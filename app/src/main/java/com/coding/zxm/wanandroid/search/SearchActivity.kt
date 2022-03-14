package com.coding.zxm.wanandroid.search

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivitySearchBinding
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.search.adapter.HotWordAdapter
import com.coding.zxm.wanandroid.search.adapter.SearchResultAdapter
import com.coding.zxm.wanandroid.search.model.HotWordEntity
import com.coding.zxm.webview.OnCollectionChangedListener
import com.coding.zxm.webview.X5WebviewActivity
import com.zxm.utils.core.keyborad.KeyboradUtil
import com.zxm.utils.core.sp.SharedPreferencesUtil
import com.zxm.utils.core.time.TimeUtil

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 * TODO:搜索历史功能后期添加；搜索结果请求逻辑存在问题
 */
class SearchActivity : BaseActivity(), View.OnClickListener {

    companion object {
        fun startSearch(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mSearchViewModel: SearchViewModel by viewModels { SearchViewModel.SearchViewModelFactory }

    //hot word
    private lateinit var mHotWordAdapter: HotWordAdapter
    private val mSearchList: MutableList<HotWordEntity> = ArrayList()

    //搜索结果
    private var mPage: Int = 0
    private val mSearchResult: MutableList<NewsDetialEntity> = ArrayList()
    private lateinit var mSearchResultAdapter: SearchResultAdapter

    private lateinit var searchBinding: ActivitySearchBinding
    override fun setContentLayout(): Any {
        searchBinding = ActivitySearchBinding.inflate(layoutInflater)
        return searchBinding.root
    }


    override fun initParamsAndValues() {
        setStatusBarColorLight()

        mHotWordAdapter = HotWordAdapter(mSearchList)

        mSearchResultAdapter = SearchResultAdapter(mSearchResult)
    }

    override fun initViews() {
        searchBinding.ivSearchBack.setOnClickListener(this)
        searchBinding.tvSearchAction.setOnClickListener(this)
        searchBinding.ivSearchClear.setOnClickListener(this)

        searchBinding.rvSearch.adapter = mHotWordAdapter
        searchBinding.rvSearch.layoutManager = LinearLayoutManager(mContext)

        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(mContext!!, R.drawable.shape_list_horizontal_divider_gray)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        searchBinding.rvSearch.addItemDecoration(itemDecoration)

        mHotWordAdapter.setOnItemClickListener { adapter, view, position ->
            val keyWord = (adapter as HotWordAdapter).getItem(position).name as String

            if (!TextUtils.isEmpty(keyWord)) {
                searchBinding.etSearch.setText(keyWord)
                doSearch(keyWord)
            }
        }


        searchBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val result = s?.toString()?.trim()
                if (!TextUtils.isEmpty(result)) {
                    searchBinding.ivSearchClear.visibility = View.VISIBLE
                    //TODO:搜索
                } else {
                    searchBinding.ivSearchClear.visibility = View.GONE
                    if (!searchBinding.layoutHotWord.isShown) {
                        searchBinding.layoutHotWord.visibility = View.VISIBLE
                    }
                    if (searchBinding.srSearchResult.isShown) {
                        searchBinding.srSearchResult.visibility = View.GONE
                    }
                    if (mSearchResult.isNotEmpty()) {
                        mSearchResult.clear()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        getHotWord()

        //search action
        //是否在加载的时候禁止列表的操作
        searchBinding.srSearchResult.setDisableContentWhenLoading(true)
        searchBinding.srSearchResult.setEnableRefresh(false)

        searchBinding.srSearchResult.setOnLoadMoreListener {
            val key = searchBinding.etSearch.editableText.toString().trim()
            doSearch(key)
        }
        searchBinding.rvSearchResult.adapter = mSearchResultAdapter
        searchBinding.rvSearchResult.layoutManager = LinearLayoutManager(mContext)
        searchBinding.rvSearchResult.addItemDecoration(itemDecoration)
        mSearchResultAdapter.setOnItemClickListener { adapter, view, position ->
            val entity = (adapter as SearchResultAdapter).getItem(position)
            entity?.let {
                val jsonData = JSON.toJSONString(entity)
                X5WebviewActivity.loadUrl(
                    mContext!!,
                    entity.title,
                    entity.link,
                    jsonData,
                    collect = it.collect,
                    callback = object :
                        OnCollectionChangedListener {
                        override fun collectionChanged() {
                            searchBinding.srSearchResult.autoRefresh(400)
                        }
                    }
                )
            }

        }

    }

    private fun getHotWord() {
        val time = SharedPreferencesUtil.get(
            mContext!!,
            SPConfig.CONFIG_WORDS_DATE,
            ""
        ) as String

        if (time.isEmpty() || !TimeUtil.isToday(time)) {
            val hotWordLiveData = mSearchViewModel.getHotWord()
            hotWordLiveData.observe(this, Observer {

                it?.let {

                    if (mSearchList.isNotEmpty()) {
                        mSearchList.clear()
                    }

                    val data = JSON.toJSONString(it)
                    SharedPreferencesUtil.put(
                        mContext!!,
                        SPConfig.CONFIG_DATA_HOT_WORDS,
                        data
                    )

                    SharedPreferencesUtil.put(
                        mContext!!,
                        SPConfig.CONFIG_WORDS_DATE,
                        "${TimeUtil.getNowMills()}"
                    )
                    mSearchList.addAll(it)
                    mHotWordAdapter.notifyDataSetChanged()
                }
            })
        } else {
            val dataArray = SharedPreferencesUtil.get(
                mContext!!,
                SPConfig.CONFIG_DATA_HOT_WORDS,
                ""
            ) as String
            if (!TextUtils.isEmpty(dataArray)) {
                val list = JSON.parseArray(dataArray, HotWordEntity::class.java)
                if (list != null && list.isNotEmpty()) {
                    if (mSearchList.isNotEmpty()) {
                        mSearchList.clear()
                    }

                    mSearchList.addAll(list)
                    mHotWordAdapter.notifyDataSetChanged()
                }
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_search_back -> {
                finish()
            }
            R.id.tv_search_action -> {
                mPage = 0
                KeyboradUtil.hideSoftInput(this)
                val key = searchBinding.etSearch.editableText.toString().trim()
                doSearch(key)
            }
            R.id.iv_search_clear -> {
                searchBinding.etSearch.editableText.clear()
                if (!searchBinding.layoutHotWord.isShown) {
                    searchBinding.layoutHotWord.visibility = View.VISIBLE
                }
                if (searchBinding.srSearchResult.isShown) {
                    searchBinding.srSearchResult.visibility = View.GONE
                }

                if (mSearchResult.isNotEmpty()) {
                    mSearchResult.clear()
                }
            }
        }
    }

    private fun doSearch(key: String) {
        if (TextUtils.isEmpty(key)) {
            Toast.makeText(mContext!!, "您是否忘记输入关键词了？", Toast.LENGTH_SHORT).show()
            return
        }

        if (searchBinding.layoutHotWord.isShown) {
            searchBinding.layoutHotWord.visibility = View.GONE
        }
        if (!searchBinding.srSearchResult.isShown) {
            searchBinding.srSearchResult.visibility = View.VISIBLE
        }

        val searchLiveData = mSearchViewModel.doSearch(mPage, key)

        mPage += 1

        searchLiveData.observe(this, Observer {

            searchBinding.srSearchResult.finishLoadMore()
            if (it == null)
                return@Observer

            val datas = it.datas
            if (datas != null && datas.isNotEmpty()) {
                mSearchResult.addAll(datas)
                mSearchResultAdapter.notifyDataSetChanged()
            }


            if (it.over) {
                searchBinding.srSearchResult.finishLoadMoreWithNoMoreData()
            }
        })

    }

    override fun onDestroy() {
        KeyboradUtil.hideSoftInput(this)
        super.onDestroy()
    }
}