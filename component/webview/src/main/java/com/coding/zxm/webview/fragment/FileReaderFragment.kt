package com.coding.zxm.webview.fragment

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsReaderView
import com.tencent.smtt.sdk.ValueCallback
import com.zxm.utils.core.log.MLogger
import java.io.File


/**
 * Created by ZhangXinmin on 2021/05/10.
 * Copyright (c) 5/10/21 . All rights reserved.
 * 文件预览功能
 */
class FileReaderFragment : Fragment(), TbsReaderView.ReaderCallback {

    companion object {
        private const val TAG = "FileReaderFragment"

        private const val PARAMS_FILE_PATH = "params_file_path"
        private const val PARAMS_FILE_NAME = "params_file_name"

        fun newInstance(filePath: String, fileName: String): FileReaderFragment {
            val fragment = FileReaderFragment()
            val args = Bundle()
            args.putString(PARAMS_FILE_PATH, filePath)
            args.putString(PARAMS_FILE_NAME, fileName)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mFilePath: String
    private lateinit var mFileName: String

    private lateinit var mContext: Context
    private lateinit var mTbsReaderView: TbsReaderView
    private lateinit var mTbsTempPath: String

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = ConstraintLayout(mContext)
        mTbsReaderView = TbsReaderView(inflater.context, this)
        rootView.addView(
            mTbsReaderView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            mFilePath = it.getString(PARAMS_FILE_PATH, "")
            mFileName = it.getString(PARAMS_FILE_NAME, "")
        }

        mTbsTempPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path + "/tbsReaderTemp"

        if (!TextUtils.isEmpty(mFilePath)) {
            displayFile(mFilePath, mFileName)
        }

    }

    private fun displayFile(filePath: String, fileName: String) {

        //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
        val bsReaderTemp: String = mTbsTempPath
        val bsReaderTempFile = File(bsReaderTemp)
        if (!bsReaderTempFile.exists()) {
            Log.d("print", "准备创建/TbsReaderTemp！！")
            val mkdir: Boolean = bsReaderTempFile.mkdir()
            if (!mkdir) {
                Log.d("print", "创建/TbsReaderTemp失败！！！！！")
            }
        }

        QbSdk.canOpenFile(mContext, filePath, object : ValueCallback<Boolean> {

            override fun onReceiveValue(p0: Boolean?) {
                MLogger.d(TAG, "onReceiveValue:${p0}")
            }

        })
        val bundle = Bundle()
        bundle.putString("filePath", filePath)
        bundle.putString("tempPath", mTbsTempPath)
        val result: Boolean = mTbsReaderView.preOpen(getFileType(fileName), false)
        Log.d("print", "查看文档---$result")
        if (result) {
            mTbsReaderView.post {
                mTbsReaderView.openFile(bundle)
            }

            //使用QQ浏览器打开
//            QbSdk.openFileWithQB(mContext, filePath, fileName)
        }
    }

    /**
     * 后缀名的判断
     *
     * @param paramString
     * @return
     */
    private fun getFileType(paramString: String): String {
        var str = ""

        if (TextUtils.isEmpty(paramString)) {
            Log.d("print", "paramString---->null");
            return str
        }
        Log.d("print", "paramString:$paramString");
        val i = paramString.lastIndexOf('.');
        if (i <= -1) {
            Log.d("print", "i <= -1");
            return str
        }

        str = paramString.substring(i + 1);
        Log.d("print", "paramString.substring(i + 1)------>$str");
        return str

    }

    override fun onDestroy() {
        mTbsReaderView?.onStop()
        super.onDestroy()
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {

    }
}