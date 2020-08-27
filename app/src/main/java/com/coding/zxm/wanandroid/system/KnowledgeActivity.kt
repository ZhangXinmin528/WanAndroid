package com.coding.zxm.wanandroid.system

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.system.adapter.KnowledgeNavAdapter
import com.coding.zxm.wanandroid.system.model.NaviKnowledgeEntity
import kotlinx.android.synthetic.main.activity_knowledge.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
class KnowledgeActivity : BaseActivity() {

    private val knowledgeViewModel: KnowledgeViewModel by viewModels { KnowledgeViewModel.KnowledgeViewModelFactory }

    //导航栏
    private val mNavDataList: MutableList<NaviKnowledgeEntity> = ArrayList()
    private lateinit var mNavAdapter: KnowledgeNavAdapter

    override fun setLayoutId(): Int {
        return R.layout.activity_knowledge
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent()

        mNavAdapter = KnowledgeNavAdapter(mNavDataList)


    }

    override fun initViews() {

        setSupportActionBar(toolbar_wan)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = "知识体系"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }

        //导航栏
        rv_knowledge_nav.adapter = mNavAdapter
        rv_knowledge_nav.layoutManager = LinearLayoutManager(mContext)

        mNavAdapter.setOnItemClickListener { adapter, view, position ->

        }

        getKnowledgeTree()

    }

    private fun getKnowledgeTree() {
        val liveData = knowledgeViewModel.getKnowledgeTree()
        liveData.observe(this, Observer {
            if (mNavDataList.isNotEmpty()) {
                mNavDataList.clear()
            }

            for (index in it.indices) {
                val item = NaviKnowledgeEntity(it[index], index == 0)
                mNavDataList.add(item)
            }

            mNavAdapter.notifyDataSetChanged()
        })
    }

}