package com.coding.zxm.wanandroid

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomePageAdapter(
    private val fragments: MutableList<Fragment>,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(
    fragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return if (fragments.isNotEmpty()) {
            fragments.size
        } else 0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}