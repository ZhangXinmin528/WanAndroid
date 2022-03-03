package com.coding.zxm.wanandroid.system

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.system.adapter.KnowledgeDetialAdapter
import com.coding.zxm.wanandroid.system.adapter.KnowledgeNavAdapter
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import com.coding.zxm.wanandroid.system.model.NaviKnowledgeEntity
import com.coding.zxm.wanandroid.system.viewmodel.KnowledgeViewModel
import kotlinx.android.synthetic.main.activity_knowledge.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 * TODO:列表联动需要优化
 */
class KnowledgeActivity : BaseActivity() {

    private val knowledgeViewModel: KnowledgeViewModel by viewModels { KnowledgeViewModel.KnowledgeViewModelFactory }

    //导航栏
    private val mNavDataList: MutableList<NaviKnowledgeEntity> = ArrayList()
    private lateinit var mNavAdapter: KnowledgeNavAdapter

    //详情
    private lateinit var mDetialAdapter: KnowledgeDetialAdapter
    private val mDetialList: MutableList<KnowledgeEntity> = ArrayList()

    override fun setLayoutId(): Int {
        return R.layout.activity_knowledge
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()
        mNavAdapter = KnowledgeNavAdapter(mNavDataList)

        mDetialAdapter = KnowledgeDetialAdapter(mDetialList)

    }

    override fun initViews() {

        tv_toolbar_title.text = getString(R.string.all_system_title)
        iv_toolbar_back.setOnClickListener { finish() }

        //导航栏
        rv_knowledge_nav.adapter = mNavAdapter
        rv_knowledge_nav.layoutManager = LinearLayoutManager(mContext)

        mNavAdapter.setOnItemClickListener { adapter, view, position ->
            selectNavItem(position)
            rv_knowledge_detial.smoothScrollToPosition(position)
        }

        //详情
        rv_knowledge_detial.adapter = mDetialAdapter
        rv_knowledge_detial.layoutManager = LinearLayoutManager(mContext)
        val itemDecoration =
            DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)

        val divider =
            ContextCompat.getDrawable(mContext!!, R.drawable.shape_gray_horizontal_divider_8dp)
        itemDecoration.setDrawable(divider!!)
        rv_knowledge_detial.addItemDecoration(itemDecoration)

        mDetialAdapter.setSystemTagClicklistener(object :
            KnowledgeDetialAdapter.OnSystemTagClickListener {
            override fun onTagItemClick(view: View, knowledgeEntity: KnowledgeEntity) {
                KnowledgeListActivity.startActicles(
                    mContext!!,
                    knowledgeEntity.name,
                    knowledgeEntity.id
                )
            }

        })

        rv_knowledge_detial.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //TODO:需要实现
//                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//                val firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
//                rv_knowledge_nav.smoothScrollToPosition(firstPosition)
//                selectNavItem(firstPosition)
            }
        })
        getKnowledgeTree()

    }

    private fun selectNavItem(position: Int) {
        if (mNavDataList.isNotEmpty()) {
            for (index in mNavDataList.indices) {
                val item = mNavDataList[index]
                item.selected = index == position
                mNavDataList.removeAt(index)
                mNavDataList.add(index, item)
            }
            mNavAdapter.notifyDataSetChanged()
        }

    }

    private fun getKnowledgeTree() {
        val liveData = knowledgeViewModel.getKnowledgeTree()
        liveData.observe(this, Observer {

            if (it == null)
                return@Observer

            if (mDetialList.isNotEmpty()) {
                mDetialList.clear()
            }
            mDetialList.addAll(it)

            mDetialAdapter.notifyDataSetChanged()

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