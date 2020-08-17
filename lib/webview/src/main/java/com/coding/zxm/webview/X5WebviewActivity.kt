package com.coding.zxm.webview

import android.text.TextUtils
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.core.base.constants.RoutePath
import com.coding.zxm.webview.fragment.X5WebViewFragment
import com.coding.zxm.webview.x5.X5WebView
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_webview.*

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
@Route(path = RoutePath.ROUTE_PATH_WEBVIEW)
class X5WebviewActivity : BaseActivity(), X5WebView.WebViewListener {

    private var mIsWebViewAvailable = false
    private var mUrl: String? = null
    private var mIsPageFinished = false

    override fun setLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initParamsAndValues() {
        val bundle = intent?.extras
        if (bundle!!.containsKey(X5WebViewFragment.PARAMS_WEBVIEW_URL)) {
            mUrl = bundle.getString(X5WebViewFragment.PARAMS_WEBVIEW_URL)
        }

        if (mUrl.isNullOrEmpty() || mUrl.isNullOrBlank()) {
            Toast.makeText(mContext, "Url is invalid!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun initViews() {
        x5webview.setWebViewListener(this)
        mIsWebViewAvailable = true
    }

    override fun onStart() {
        super.onStart()
        if (mIsWebViewAvailable && !TextUtils.isEmpty(mUrl)
        ) {
            x5webview.loadUrl(mUrl)
        }
    }

    override fun onPause() {
        x5webview.onPause()
        super.onPause()
    }

    override fun onResume() {
        x5webview.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        x5webview.destroy()
        super.onDestroy()
    }


    override fun onProgressChange(view: WebView?, newProgress: Int) {

    }

    override fun onPageFinish(view: WebView?) {
        mIsPageFinished = true
    }

}