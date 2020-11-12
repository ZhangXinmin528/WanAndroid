package com.coding.zxm.wanandroid.gallery

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.gallery.adapter.BingWallpapersAdapter
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import kotlinx.android.synthetic.main.fragment_bings_wallpapers.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/12 . All rights reserved.
 * Bing 壁纸列表页面
 */
class BingWallpapersFragment : BaseFragment() {
    private val mBingViewModel: BingViewModel by viewModels { BingViewModel.BingViewModelFactory }

    companion object {
        fun newInstance(): BingWallpapersFragment {
            return BingWallpapersFragment()
        }
    }

    private val mDataList: MutableList<BingImageEntity> = ArrayList()
    private lateinit var mAdapter: BingWallpapersAdapter

    override fun setLayoutId(): Int {
        return R.layout.fragment_bings_wallpapers
    }

    override fun initParamsAndValues() {
        mAdapter = BingWallpapersAdapter(mDataList)

    }

    override fun initViews(rootView: View) {

        //是否在刷新的时候禁止列表的操作
        sr_bing_layout.setDisableContentWhenRefresh(true)

        //延迟400毫秒后自动刷新
        sr_bing_layout.autoRefresh(600)

        sr_bing_layout.setOnRefreshListener {
            requestBingData()
        }

        rv_bing.layoutManager = LinearLayoutManager(mContext)
        rv_bing.adapter = mAdapter

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
                if (it == null)
                    return@Observer
                if (it != null && it.isNotEmpty()) {
                    mDataList.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }

                sr_bing_layout.finishRefresh()
            })
    }

}