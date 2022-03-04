package com.coding.zxm.wanandroid.gallery

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.wanandroid.BaseStatusBarFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.FragmentBingsWallpapersBinding
import com.coding.zxm.wanandroid.gallery.adapter.BingWallpapersAdapter
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.zxm.utils.core.bar.StatusBarCompat.getStatusBarHeight

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/12 . All rights reserved.
 * Bing 壁纸列表页面
 */
class BingWallpapersFragment : BaseStatusBarFragment() {
    private val mBingViewModel: BingViewModel by viewModels { BingViewModel.BingViewModelFactory }

    companion object {
        fun newInstance(): BingWallpapersFragment {
            return BingWallpapersFragment()
        }
    }

    private val mDataList: MutableList<BingImageEntity> = ArrayList()
    private lateinit var mAdapter: BingWallpapersAdapter
    private lateinit var bingsBinding: FragmentBingsWallpapersBinding

    override fun setContentLayout(): View {
        bingsBinding = FragmentBingsWallpapersBinding.inflate(layoutInflater)
        return bingsBinding.root
    }

    override fun initParamsAndValues() {
        mAdapter = BingWallpapersAdapter(mDataList)

    }

    override fun initViews() {
        val layoutParams = bingsBinding.fakeStatusBar.fakeStatusBar.layoutParams
        layoutParams.height = getStatusBarHeight(mContext!!)
        bingsBinding.fakeStatusBar.fakeStatusBar.layoutParams = layoutParams

        setFakeStatusColor(bingsBinding.fakeStatusBar.fakeStatusBar, R.color.color_toolbar_light)

        //是否在刷新的时候禁止列表的操作
        bingsBinding.srBingsLayout.setDisableContentWhenRefresh(true)

        //延迟400毫秒后自动刷新
        bingsBinding.srBingsLayout.autoRefresh(600)

        bingsBinding.srBingsLayout.setOnRefreshListener {
            requestBingData()
        }

        bingsBinding.rvBing.layoutManager = LinearLayoutManager(mContext)
        bingsBinding.rvBing.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val imageEntity = (adapter as BingWallpapersAdapter).getItem(position)
            if (imageEntity != null) {
                mContext?.let { ImagePreviewActivity.previewImage(it, imageEntity) }
            }

        }
    }

    private fun requestBingData() {
        if (mDataList.isNotEmpty()) {
            mDataList.clear()
            mAdapter.notifyDataSetChanged()
        }

        mBingViewModel.getBingPicList()
            .observe(this, Observer {
                bingsBinding.srBingsLayout.finishRefresh()
                if (it == null)
                    return@Observer
                if (it != null && it.isNotEmpty()) {
                    mDataList.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }
            })
    }

}