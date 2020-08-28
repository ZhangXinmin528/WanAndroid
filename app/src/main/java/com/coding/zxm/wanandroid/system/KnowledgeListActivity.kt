package com.coding.zxm.wanandroid.system

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.activity.viewModels
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.system.viewmodel.KnowledgeListViewModel
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

    override fun setLayoutId(): Int {
        return R.layout.activity_knowledge_list
    }

    override fun initParamsAndValues() {
        if (intent != null) {
            mTitle = intent.getStringExtra(PARAMAS_TITLE)
            mCid = intent.getIntExtra(PARAMAS_CID, 0)
        }


    }

    override fun initViews() {
        setSupportActionBar(toolbar_wan)
        val actionBar = supportActionBar
        actionBar?.let {
            actionBar.title = if (TextUtils.isEmpty(mTitle)) "知识体系专栏" else "${mTitle}专栏"
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }


    }

    private fun getArticles() {
        mCid?.let {
            val liveData = mListViewModel.getKnowledgeArticles(mCurrentPage, it)

        }

    }
}