package com.coding.zxm.webview

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.UriUtils
import com.coding.zxm.webview.fragment.FileReaderFragment
import com.zxm.utils.core.log.MLogger
import kotlinx.android.synthetic.main.activity_file_reader.*

/**
 * Created by ZhangXinmin on 2021/05/11.
 * Copyright (c) 5/11/21 . All rights reserved.
 */
class FileReaderActivity : BaseActivity(), View.OnClickListener {

    private var mFilePath: String? = ""
    private var mFileName: String = ""

    override fun setLayoutId(): Int {
        return R.layout.activity_file_reader
    }

    override fun initParamsAndValues() {
        setStatusBarDark()

        ib_reader_back.setOnClickListener(this)

        intent?.let {
            val action = intent.action
            if (!TextUtils.isEmpty(action)
                && Intent.ACTION_VIEW == action
            ) {
                val uri = intent.data
                val path = Uri.decode(uri?.encodedPath)

                mFilePath = UriUtils.getFilePathFromUri(mContext!!, uri!!)
                mFileName = getFileName()
                mFileName = if (TextUtils.isEmpty(mFileName)) "文档查看" else mFileName
                tv_reader_title.text = mFileName
                MLogger.d("reader", "path:${path}..mFilePath:${mFilePath}")
            }
        }

    }

    override fun initViews() {

        if (!TextUtils.isEmpty(mFilePath)) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container_reader,
                    FileReaderFragment.newInstance(mFilePath!!, "test.pptx")
                )
                .commit()
        } else {
            Toast.makeText(mContext, "文件路径获取异常！", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileName(): String {
        if (!TextUtils.isEmpty(mFilePath) && mFilePath!!.contains("/")) {
            val temp = mFilePath!!.split("/")
            return temp[temp.size - 1]
        }
        return ""
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ib_reader_back -> {
                finish()
            }
        }
    }

}