package com.coding.zxm.wanandroid.home

import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.webview.X5WebviewActivity
import com.youth.banner.indicator.RoundLinesIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 */
class HomeFragment private constructor() : BaseFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels { HomeViewModel.HomeViewModelFactory }

    private var mBannerList: MutableList<BannerEntity> = ArrayList()

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initParamsAndValues() {

        val bannerList = mHomeViewModel.getBannerData().value

        bannerList?.let {
            mBannerList.addAll(bannerList)
        }

    }

    override fun initViews(rootView: View) {

        val bannerAdapter = BannerImageAdapter(mBannerList)
        banner_home?.let {
            it.adapter = bannerAdapter
            it.addBannerLifecycleObserver(this)
            it.indicator = RoundLinesIndicator(mContext)
            it.setBannerRound(20f)
            it.setOnBannerListener(object : OnBannerListener<BannerEntity> {
                override fun OnBannerClick(data: BannerEntity?, position: Int) {
                    data?.let {
                        val intent = Intent(mContext, X5WebviewActivity::class.java)
                        startActivity(intent)
                    }
                }
            })
        }
    }

    override fun onStart() {
        banner_home?.start()
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        banner_home?.stop()
    }

    override fun onDestroyView() {
        banner_home?.destroy()
        super.onDestroyView()
    }
}