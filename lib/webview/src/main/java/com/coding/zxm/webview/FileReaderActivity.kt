package com.coding.zxm.webview

import android.os.Environment
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.webview.fragment.FileReaderFragment
import com.tencent.smtt.sdk.QbSdk
import com.zxm.utils.core.log.MLogger

/**
 * Created by ZhangXinmin on 2021/05/11.
 * Copyright (c) 5/11/21 . All rights reserved.
 */
class FileReaderActivity : BaseActivity() {

    override fun setLayoutId(): Int {
        return R.layout.activity_file_reader
    }

    override fun initParamsAndValues() {
        setStatusBarDark()
        MLogger.d("TBSversion:" + QbSdk.getTbsVersion(mContext))
    }

    override fun initViews() {
        val filePath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/test.pptx"
//
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_reader,
                FileReaderFragment.newInstance(filePath, "test.pptx")
            )
            .commit()
    }
}