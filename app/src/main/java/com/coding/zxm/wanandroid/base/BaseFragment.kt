package com.coding.zxm.wanandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by ZhangXinmin on 2020/7/19.
 * Copyright (c) 2020 . All rights reserved.
 */
abstract class BaseFragment() : Fragment() {

    protected val TAG = this.javaClass.simpleName

    protected var mContext: Context? = null

    protected lateinit var rootView: View

    abstract fun setLayoutId(): Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(setLayoutId(), container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initParamsAndValues()

        initViews(rootView)
    }

    abstract fun initParamsAndValues();

    abstract fun initViews(rootView: View);
}