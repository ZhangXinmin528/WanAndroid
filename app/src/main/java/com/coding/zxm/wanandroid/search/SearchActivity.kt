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
import com.coding.zxm.util.SharedPreferenceConfig
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.search.adapter.HotWordAdapter
import com.coding.zxm.wanandroid.search.model.HotWordEntity
import com.zxm.utils.core.sp.SharedPreferencesUtil
import com.zxm.utils.core.time.TimeUtil
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Created by ZhangXinmin on 2020/8/19.
 * Copyright (c) 2020 . All rights reserved.
 * TODO:搜索历史功能后期添加
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

    override fun setLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun initParamsAndValues() {
        setStateBarColor()

        mHotWordAdapter = HotWordAdapter(mSearchList)

    }

    override fun initViews() {
        iv_search_back.setOnClickListener(this)
        tv_search_action.setOnClickListener(this)

        rv_search.adapter = mHotWordAdapter
        rv_search.layoutManager = LinearLayoutManager(mContext)

        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(mContext!!, R.drawable.icon_search_divider)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        rv_search.addItemDecoration(itemDecoration)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val result = s?.toString()?.trim()
                if (!TextUtils.isEmpty(result)) {
                    iv_search_clear.visibility = View.VISIBLE
                    //TODO:搜索
                } else {
                    iv_search_clear.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        getHotWord()
    }

    private fun getHotWord() {
        val time = SharedPreferencesUtil.get(
            mContext!!,
            SharedPreferenceConfig.CONFIG_WORDS_DATE,
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
                        SharedPreferenceConfig.CONFIG_DATA_HOT_WORDS,
                        data
                    )

                    SharedPreferencesUtil.put(
                        mContext!!,
                        SharedPreferenceConfig.CONFIG_WORDS_DATE,
                        "${TimeUtil.getNowMills()}"
                    )
                    mSearchList.addAll(it)
                    mHotWordAdapter.notifyDataSetChanged()
                }
            })
        } else {
            val dataArray = SharedPreferencesUtil.get(
                mContext!!,
                SharedPreferenceConfig.CONFIG_DATA_HOT_WORDS,
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
                val key = et_search.editableText.toString().trim()
                doSearch(key)
            }
            R.id.iv_search_clear -> {
                et_search.editableText.clear()
            }
        }
    }

    private fun doSearch(key: String) {
        if (TextUtils.isEmpty(key)) {
            Toast.makeText(mContext!!, "您是否忘记输入关键词了？", Toast.LENGTH_SHORT).show()
            return
        }

        val searchLiveData = mSearchViewModel.doSearch(mPage, key)
        searchLiveData.observe(this, Observer {

        })

    }
}