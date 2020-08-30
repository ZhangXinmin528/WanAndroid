package com.coding.zxm.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.zxm.utils.core.bar.StatusBarCompat

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

    protected fun setStatusBarColorNoTranslucent(@ColorRes colorRes: Int) {
        activity?.let {
            StatusBarCompat.setColorNoTranslucent(
                activity,
                resources.getColor(colorRes)
            )
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                activity!!.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    abstract fun initParamsAndValues();

    abstract fun initViews(rootView: View);
}