package com.coding.zxm.wanandroid.project

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.FragmentProjectItemBinding
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.home.model.NewsEntity
import com.coding.zxm.wanandroid.project.adapter.ProjectItemAdapter
import com.coding.zxm.webview.OnCollectionChangedListener
import com.coding.zxm.webview.X5WebviewActivity

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

    private lateinit var itemBinding: FragmentProjectItemBinding

    override fun setContentLayout(container: ViewGroup?): View {
        itemBinding = FragmentProjectItemBinding.inflate(layoutInflater, container, false)
        return itemBinding.root
    }

    override fun initParamsAndValues() {
        if (arguments != null) {
            mCid = requireArguments().getInt(PARAMS_PRJECT_CID)
        }
//        mViewModel =
//            ViewModelProvider(requireActivity()).get("itemId:${mCid}", ProjectViewModel::class.java)

        mProjectAdapter = ProjectItemAdapter(mDataList = mDataList)
    }


    override fun initViews() {

        itemBinding.rvFragmentProject.layoutManager = LinearLayoutManager(mContext)
        itemBinding.rvFragmentProject.adapter = mProjectAdapter
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
        itemBinding.rvFragmentProject.addItemDecoration(itemDecoration)

        mProjectAdapter.setOnItemClickListener { adapter, view, position ->
            val newsDetialEntity = (adapter as ProjectItemAdapter).data[position]
            val jsonData = JSON.toJSONString(newsDetialEntity)
            X5WebviewActivity.loadUrl(
                mContext!!,
                newsDetialEntity.title,
                newsDetialEntity.link,
                jsonData,
                collect = newsDetialEntity.collect,
                callback = object :
                    OnCollectionChangedListener {
                    override fun collectionChanged() {
                        itemBinding.srProjectLayout.autoRefresh(400)
                    }
                }
            )
        }

        //是否在刷新的时候禁止列表的操作
        itemBinding.srProjectLayout.setDisableContentWhenRefresh(true)
        //是否在加载的时候禁止列表的操作
        itemBinding.srProjectLayout.setDisableContentWhenLoading(true)

        //延迟400毫秒后自动刷新
        itemBinding.srProjectLayout.autoRefresh(400)

        itemBinding.srProjectLayout.setOnRefreshListener {
            requestProjectData(true)
        }

        itemBinding.srProjectLayout.setOnLoadMoreListener {
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
                itemBinding.srProjectLayout?.finishRefresh()
            } else {
                itemBinding.srProjectLayout?.finishLoadMore()
            }

            val datas = it.datas
            if (datas.isNotEmpty()) {
                mDataList.addAll(datas)
                mProjectAdapter.notifyDataSetChanged()
            }

            //没有更多数据
            if (it.over) {
                itemBinding.srProjectLayout?.finishLoadMoreWithNoMoreData()
            }
        })

    }
}