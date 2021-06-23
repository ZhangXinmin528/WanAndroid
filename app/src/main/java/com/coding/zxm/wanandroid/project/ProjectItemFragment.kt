package com.coding.zxm.wanandroid.project

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.project.adapter.ProjectItemAdapter
import com.coding.zxm.webview.X5WebviewActivity
import kotlinx.android.synthetic.main.fragment_project_item.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/4 . All rights reserved.
 */
class ProjectItemFragment : BaseFragment() {

    private val mViewModel: ProjectViewModel by viewModels { ProjectViewModel.ProjectViewModelFactory }

    companion object {
        private const val PARAMS_PRJECT_CID = "params_project_cid"

        fun newInstance(cid: Int): ProjectItemFragment {
            val fragment = ProjectItemFragment()
            val bundle = Bundle()
            bundle.putInt(PARAMS_PRJECT_CID, cid)
            fragment.arguments = bundle
            return fragment
        }

    }

    private var mCurrentPage: Int = 0
    private var mCid: Int = 0

    private lateinit var mProjectAdapter: ProjectItemAdapter
    private val mDataList: MutableList<NewsDetialEntity> = ArrayList()

    override fun setLayoutId(): Int {
        return R.layout.fragment_project_item
    }

    override fun initParamsAndValues() {
        if (arguments != null) {
            mCid = arguments!!.getInt(PARAMS_PRJECT_CID)
        }
//        mViewModel =
//            ViewModelProvider(requireActivity()).get("itemId:${mCid}", ProjectViewModel::class.java)

        mProjectAdapter = ProjectItemAdapter(mDataList = mDataList)
    }

    override fun initViews(rootView: View) {

        rv_fragment_project.layoutManager = LinearLayoutManager(mContext)
        rv_fragment_project.adapter = mProjectAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(context!!, R.drawable.shape_list_horizontal_divider_gray)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        rv_fragment_project.addItemDecoration(itemDecoration)

        mProjectAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as ProjectItemAdapter).data[position]

            X5WebviewActivity.loadUrl(mContext!!, newsDetialEntity.title, newsDetialEntity.link)
        }

        //是否在刷新的时候禁止列表的操作
        sr_project_layout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        sr_project_layout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        sr_project_layout.autoRefresh(400)

        sr_project_layout.setOnRefreshListener {
            requestProjectData(true)
        }

        sr_project_layout.setOnLoadMoreListener {
            requestProjectData(false)
        }

    }

    private fun requestProjectData(isRefresh: Boolean) {
        if (isRefresh) {
            mCurrentPage = 0
            if (mDataList.isNotEmpty()) {
                mDataList.clear()
                mProjectAdapter.notifyDataSetChanged()
            }
        }

        if (mCid == 0)
            return

        val liveData: MutableLiveData<NewsEntity> =
            mViewModel.getProjectList(mCurrentPage, mCid)

        mCurrentPage += 1

        liveData.observeForever(Observer {
            if (it == null)
                return@Observer

            if (isRefresh) {
                sr_project_layout?.finishRefresh()
            } else {
                sr_project_layout?.finishLoadMore()
            }

            val datas = it.datas
            if (datas.isNotEmpty()) {
                mDataList.addAll(datas)
                mProjectAdapter.notifyDataSetChanged()
            }

            //没有更多数据
            if (it.over) {
                sr_project_layout?.finishLoadMoreWithNoMoreData()
            }
        })

    }
}