package com.coding.zxm.webview.fragment

import android.view.View
import com.coding.zxm.core.base.BaseFragment
import com.coding.zxm.webview.R
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsReaderView
import com.tencent.smtt.sdk.ValueCallback
import kotlinx.android.synthetic.main.fragment_file_reader.*

/**
 * Created by ZhangXinmin on 2021/05/10.
 * Copyright (c) 5/10/21 . All rights reserved.
 * 文件预览功能
 */
class FileReaderFragment : BaseFragment() {

    companion object {
        fun newInstance(): FileReaderFragment {
            return FileReaderFragment()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.fragment_file_reader
    }

    override fun initParamsAndValues() {

    }

    override fun initViews(rootView: View) {
//        TbsReaderView
//        QbSdk.openFileReader(mContext,"",object :ValueCallback<>)
    }

    override fun onDestroy() {

        super.onDestroy()
    }
}