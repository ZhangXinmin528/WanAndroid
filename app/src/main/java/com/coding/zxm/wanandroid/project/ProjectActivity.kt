package com.coding.zxm.wanandroid.project

import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.project.adapter.ProjectTabAdapter
import com.zxm.utils.core.sp.SharedPreferencesUtil
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
class ProjectActivity : BaseActivity() {

    private val mViewModel: ProjectViewModel by viewModels { ProjectViewModel.ProjectViewModelFactory }

    private lateinit var mTabAdapter: ProjectTabAdapter

    private val fragments: MutableList<Fragment> = ArrayList()

    override fun setLayoutId(): Int {
        return R.layout.activity_project
    }

    override fun initParamsAndValues() {

        val liveData = mViewModel.getProjectTree()
        liveData.observe(this, Observer {
//            SharedPreferencesUtil.put(mContext,)
        })

        mTabAdapter = ProjectTabAdapter(fragments, fragmentManager = supportFragmentManager)
    }

    override fun initViews() {
        initActionBar(toolbar_wan, "项目")

        tab_project.setupWithViewPager(vp_project)
        vp_project.adapter = mTabAdapter
    }
}