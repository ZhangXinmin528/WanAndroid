package com.coding.zxm.wanandroid.home

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.coding.zxm.map.LocationManager
import com.coding.zxm.map.location.listener.OnLocationListener
import com.coding.zxm.wanandroid.BaseStatusBarFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.app.WanApp
import com.coding.zxm.wanandroid.home.adapter.HomeNewsAdapter
import com.coding.zxm.wanandroid.home.model.BannerEntity
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.search.SearchActivity
import com.coding.zxm.wanandroid.weather.WeatherActivity
import com.coding.zxm.weather.WeatherManager
import com.coding.zxm.weather.entity.WeatherNowEntity
import com.coding.zxm.weather.listener.OnWeatherResultListener
import com.coding.zxm.webview.X5WebviewActivity
import com.sunfusheng.marqueeview.MarqueeView
import com.youth.banner.Banner
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.zxm.utils.core.bar.StatusBarCompat.getStatusBarHeight
import com.zxm.utils.core.log.MLogger
import com.zxm.utils.core.screen.ScreenUtil
import kotlinx.android.synthetic.main.fragment_home_behavior.*
import kotlinx.android.synthetic.main.layout_fake_status_bar.*
import kotlinx.android.synthetic.main.layout_home_banner.*
import kotlinx.android.synthetic.main.layout_home_banner.view.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020 . All rights reserved.
 * TODO：功能列表后面做
 */
class HomeFragment() : BaseStatusBarFragment() {

    private val mHomeViewModel: HomeViewModel by viewModels { HomeViewModel.HomeViewModelFactory }
    private val mNewsList: MutableList<NewsDetialEntity> = ArrayList()
    private var mCurrentPage: Int = 0
    private lateinit var mNewsAdapter: HomeNewsAdapter

    private val mMarqueeList: MutableList<String> = ArrayList()
    private var mMarqueeView: MarqueeView<String>? = null

    private lateinit var mBannerContainer: ConstraintLayout
    private lateinit var mBanner: Banner<*, *>

    companion object {

        val COLORS_RES = mutableListOf(
            R.color.app_status_bar_1, R.color.app_status_bar_2, R.color.app_status_bar_3,
            R.color.app_status_bar_4, R.color.app_status_bar_5,
        )

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

    }

    override fun setLayoutId(): Int = R.layout.fragment_home_behavior

    override fun initParamsAndValues() {
        mNewsAdapter = HomeNewsAdapter(mNewsList)
    }

    override fun initViews(rootView: View) {

        val layoutParams = fake_status_bar.layoutParams
        layoutParams.height = getStatusBarHeight(mContext!!)
        fake_status_bar.layoutParams = layoutParams

        mMarqueeView = view?.findViewById(R.id.marquee_weather)

        mBannerContainer =
            layoutInflater.inflate(R.layout.layout_home_banner, null) as ConstraintLayout

        mBanner = mBannerContainer.findViewById(R.id.banner_home)

        setTitleBarColor(COLORS_RES[0])

        val lp: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            ScreenUtil.dp2px(mContext!!, 204f)
        )

        mBannerContainer.layoutParams = lp

        val bannerLiveData = mHomeViewModel.getBannerData()
        bannerLiveData.observe(this, Observer {
            it?.let {
                val bannerAdapter = BannerImageAdapter(it)
                mBanner.addBannerLifecycleObserver(this)
                mBanner.indicator = RectangleIndicator(mContext)
                mBanner.adapter = bannerAdapter
                mBanner.setOnBannerListener(object : OnBannerListener<BannerEntity> {
                    override fun OnBannerClick(data: BannerEntity?, position: Int) {
                        data?.let {

                            X5WebviewActivity.loadUrl(mContext!!, data.title, data.url)
                        }
                    }

                })
                mBanner.addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        val index = if (position > COLORS_RES.size) {
                            position % COLORS_RES.size
                        } else {
                            position
                        }
                        setTitleBarColor(COLORS_RES[index])
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                })

                mBanner.isAutoLoop(true)
            }
        })

        mNewsAdapter.addHeaderView(mBannerContainer)
        rv_fragment_home.layoutManager = LinearLayoutManager(mContext)
        rv_fragment_home.adapter = mNewsAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(requireContext(), R.drawable.shape_list_horizontal_divider_gray)
            ?.let {
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
            refreshLocationAndWeather()
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

        layout_home_weather.setOnClickListener {
            val weather = Intent(mContext!!, WeatherActivity::class.java)
            startActivity(weather)
        }

    }

    private fun refreshLocationAndWeather() {
        LocationManager.INSTANCE.initClient(WanApp.getApplicationContext())
            .setOnceLocationOption()
            .startLocation(object : OnLocationListener {

                override fun onLocationSuccess(location: AMapLocation) {
                    tv_location_city.text = location.city
                    getWeatherNow(location.longitude, location.latitude)
                }

                override fun onLocationFailure(errorCode: Int, errorMsg: String) {
                    Toast.makeText(
                        mContext,
                        "${getString(R.string.all_location_failed_reason)}$errorMsg",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun requestNewsData(isRefresh: Boolean) {
        if (isRefresh) {
            mCurrentPage = 0
            if (mNewsList.isNotEmpty()) {
                mNewsList.clear()
                mNewsAdapter.notifyDataSetChanged()
            }
        }

        val newsLiveData: MutableLiveData<NewsEntity> = mHomeViewModel.getNewsData(mCurrentPage)

        mCurrentPage += 1

        newsLiveData.observeForever(Observer {

            if (isRefresh) {
                sr_home_layout?.finishRefresh()
            } else {
                sr_home_layout?.finishLoadMore()
            }

            if (it == null)
                return@Observer

            val datas = it.datas
            MLogger.d(TAG, "requestNewsData..observe..it.curPage : ${it.curPage}")
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
                                if (mMarqueeList.isNotEmpty()) {
                                    mMarqueeList.clear()
                                }
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
        mBanner.start()
//        mMarqueeView?.startFlipping()
        super.onStart()
    }

    override fun onStop() {
        mBanner.stop()
        mMarqueeView?.stopFlipping()
        super.onStop()
    }


    private fun setTitleBarColor(@DrawableRes resId: Int) {
        layout_home_title.setBackgroundResource(resId)
        fake_status_bar.setBackgroundResource(resId)
        mBannerContainer.setBackgroundResource(resId)
    }

    override fun onDestroyView() {
        mBanner.destroy()
        super.onDestroyView()
    }
}