package com.coding.zxm.wanandroid.home

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.map.LocationManager
import com.coding.zxm.map.location.listener.OnLocationListener
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.adapter.HomeNewsAdapter
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.search.SearchActivity
import com.coding.zxm.weather.WeatherManager
import com.coding.zxm.weather.entity.WeatherNowEntity
import com.coding.zxm.weather.listener.OnWeatherResultListener
import com.coding.zxm.webview.X5WebviewActivity
import com.sunfusheng.marqueeview.MarqueeView
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 * TODO:首页数据逻辑存在问题
 * TODO：功能列表后面做
 * TODO:Banner随列表滑动
 */
class HomeFragment private constructor() : BaseFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels { HomeViewModel.HomeViewModelFactory }
    private val mNewsList: MutableList<NewsDetialEntity> = ArrayList()
    private var mCurrentPage: Int = 0
    private lateinit var mNewsAdapter: HomeNewsAdapter

    private val mMarqueeList: MutableList<String> = ArrayList()
    private var mMarqueeView: MarqueeView<String>? = null

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_home

    override fun initParamsAndValues() {
        mNewsAdapter = HomeNewsAdapter(mNewsList)


    }

    override fun initViews(rootView: View) {
        mMarqueeView = view?.findViewById(R.id.marquee_weather)

        LocationManager.INSTANCE.initClient(WanApp.getApplicationContext())
            .setOnceLocationOption()
            .startLocation(object : OnLocationListener {

                override fun onLocationSuccess(location: AMapLocation) {
                    tv_location_city.text = location.city
                    getWeatherNow(location.longitude, location.latitude)
                }

                override fun onLicationFailure(errorCode: Int, errorMsg: String) {
                    Toast.makeText(
                        mContext,
                        "${getString(R.string.all_location_failed_reason)}$errorMsg",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        val bannerLiveData = mHomeViewModel.getBannerData()
        bannerLiveData.observe(this, Observer {
            it?.let {
                val bannerAdapter = BannerImageAdapter(it)
                banner_home?.addBannerLifecycleObserver(this)
                banner_home?.indicator = RectangleIndicator(mContext)
                banner_home?.setBannerRound(20f)
                banner_home?.adapter = bannerAdapter
                banner_home?.setOnBannerListener(object : OnBannerListener<BannerEntity> {
                    override fun OnBannerClick(data: BannerEntity?, position: Int) {
                        data?.let {

                            X5WebviewActivity.loadUrl(mContext!!, data.title, data.url)
                        }
                    }

                })

                banner_home?.isAutoLoop(true)
            }
        })

        rv_fragment_home.layoutManager = LinearLayoutManager(mContext)
        rv_fragment_home.adapter = mNewsAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(context!!, R.drawable.icon_search_divider)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        rv_fragment_home.addItemDecoration(itemDecoration)

        mNewsAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as HomeNewsAdapter).data[position]

            X5WebviewActivity.loadUrl(mContext!!, newsDetialEntity.title, newsDetialEntity.link)
        }

        //是否在刷新的时候禁止列表的操作
        sr_home_layout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        sr_home_layout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        sr_home_layout.autoRefresh(600)

        sr_home_layout.setOnRefreshListener {
            requestNewsData(true)
        }

        sr_home_layout.setOnLoadMoreListener {
            requestNewsData(false)
        }

        layout_home_search.setOnClickListener {
            mContext?.let {
                SearchActivity.startSearch(mContext!!)
            }

        }

    }

    private fun requestNewsData(isRefresh: Boolean) {
        if (isRefresh) {
            mCurrentPage = 0
            if (mNewsList.isNotEmpty()) {
                mNewsList.clear()
                mNewsAdapter.notifyDataSetChanged()
            }
        }

        Log.d("zxm==", "requestNewsData..page : $mCurrentPage")

        val newsLiveData: MutableLiveData<NewsEntity> = mHomeViewModel.getNewsData(mCurrentPage)

        mCurrentPage += 1

        newsLiveData.observeForever(Observer {
            if (it == null)
                return@Observer

            if (isRefresh) {
                sr_home_layout?.finishRefresh()
            } else {
                sr_home_layout?.finishLoadMore()
            }

            val datas = it.datas
            Log.d("zxm==", "requestNewsData..observe..it.curPage : ${it.curPage}")
            if (datas.isNotEmpty()) {
                mNewsList.addAll(datas)
                mNewsAdapter.notifyDataSetChanged()
            }

            //没有更多数据
            if (it.over) {
                sr_home_layout.finishLoadMoreWithNoMoreData()
            }
        })

    }

    private fun getWeatherNow(longitude: Double, latitude: Double) {
        WeatherManager.INSTANCE
            .getWeatherNow(longitude, latitude, object : OnWeatherResultListener {
                override fun onError(throwable: Throwable?) {
                    Toast.makeText(
                        mContext,
                        getString(
                            R.string.all_weather_failed_reason,
                            if (throwable != null) throwable.message else ""
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onSuccess(result: String) {
                    if (TextUtils.isEmpty(result))
                        return

                    val weatherEntity = JSON.parseObject(result, WeatherNowEntity::class.java)
                    if (weatherEntity != null) {
                        if (weatherEntity.code == "200") {
                            val nowBaseBean = weatherEntity.now
                            if (nowBaseBean != null) {
                                mMarqueeList.add("${nowBaseBean.text!!} ${nowBaseBean.temp!!}°")
                                mMarqueeList.add("${nowBaseBean.windDir}${nowBaseBean.windScale}级")

                                mMarqueeView?.startWithList(mMarqueeList)
                            }

                        }
                    }
                }

            })
    }

    override fun onStart() {
        banner_home?.start()
//        mMarqueeView?.startFlipping()
        super.onStart()
    }

    override fun onStop() {
        banner_home?.stop()
        mMarqueeView?.stopFlipping()
        super.onStop()
    }

    override fun onDestroyView() {
        banner_home?.destroy()
        super.onDestroyView()
    }
}