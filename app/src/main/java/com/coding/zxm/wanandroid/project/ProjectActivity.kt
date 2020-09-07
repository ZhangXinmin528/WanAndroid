package com.coding.zxm.wanandroid.project

import android.text.TextUtils
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.SharedPreferenceConfig
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.project.adapter.ProjectTabAdapter
import com.coding.zxm.wanandroid.project.model.ProjectEntity
import com.zxm.utils.core.sp.SharedPreferencesUtil
import com.zxm.utils.core.time.TimeUtil
import kotlinx.android.synthetic.main.activity_project.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 * TODO:标签编辑
 */
class ProjectActivity : BaseActivity() {

    private val mViewModel: ProjectViewModel by viewModels { ProjectViewModel.ProjectViewModelFactory }

    private lateinit var mTabAdapter: ProjectTabAdapter

    private val mFragments: MutableList<Fragment> = ArrayList()
    private val mTagList: MutableList<ProjectEntity> = ArrayList()

    override fun setLayoutId(): Int {
        return R.layout.activity_project
    }

    override fun initParamsAndValues() {

        val time =
            SharedPreferencesUtil.get(
                mContext!!,
                SharedPreferenceConfig.CONFIG_PROJECT_DATE,
                ""
            ) as String

        if (!TextUtils.isEmpty(time) && TimeUtil.isToday(time)) {
            val data = SharedPreferencesUtil.get(
                mContext!!,
                SharedPreferenceConfig.CONFIG_PROJECT_TAGS,
                ""
            ) as String


            if (!TextUtils.isEmpty(data)) {
                val temp = JSON.parseArray(data, ProjectEntity::class.java)
                if (temp != null && temp.isNotEmpty()) {

                    if (mTagList.isNotEmpty()) {
                        mTagList.clear()
                    }
                    mTagList.addAll(temp)

                    initTagTab()
                }
            }

        } else {
            val liveData = mViewModel.getProjectTree()
            liveData.observe(this, Observer {

                if (mTagList.isNotEmpty()) {
                    mTagList.clear()
                }
                mTagList.addAll(it)

                initTagTab()

                SharedPreferencesUtil.put(
                    mContext!!,
                    SharedPreferenceConfig.CONFIG_PROJECT_TAGS,
                    JSON.toJSONString(it)
                )

                SharedPreferencesUtil.put(
                    mContext!!,
                    SharedPreferenceConfig.CONFIG_PROJECT_DATE,
                    TimeUtil.getNowString()
                )
            })
        }


    }

    private fun initTagTab() {
        mTagList.forEach {
            mFragments.add(ProjectItemFragment.newInstance(it.id))
        }
        mTabAdapter =
            ProjectTabAdapter(
                mFragments,
                mTagList,
                fragmentManager = supportFragmentManager
            )

        tab_project.setupWithViewPager(vp_project)
        vp_project.adapter = mTabAdapter
    }

    override fun initViews() {
        initActionBar(toolbar_wan, "项目")

    }
}