package com.coding.zxm.wanandroid.project.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.coding.zxm.wanandroid.project.model.ProjectEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/3 . All rights reserved.
 */
class ProjectTabAdapter(
    private val fragments: MutableList<Fragment>,
    private val tagList: MutableList<ProjectEntity>,
    private val fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return tagList[position].name
    }
}