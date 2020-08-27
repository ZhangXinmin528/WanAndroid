package com.coding.zxm.webview

import android.content.Context
import android.content.Intent
import android.text.Html
import android.text.TextUtils
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.core.base.constants.RoutePath
import com.coding.zxm.webview.fragment.X5WebViewFragment.Companion.PARAMS_WEBVIEW_TITLE
import com.coding.zxm.webview.fragment.X5WebViewFragment.Companion.PARAMS_WEBVIEW_URL
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
    private var mTitle: String? = null

    companion object {
        fun loadUrl(context: Context, title: String?, url: String) {
            val intent = Intent(context, X5WebviewActivity::class.java)
            intent.putExtra(PARAMS_WEBVIEW_URL, url)
            intent.putExtra(PARAMS_WEBVIEW_TITLE, title)
            context.startActivity(intent)
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_webview
    }

    override fun initParamsAndValues() {
        setStatusBarColorNoTranslucent()

        mUrl = intent.getStringExtra(PARAMS_WEBVIEW_URL)
        mTitle = intent.getStringExtra(PARAMS_WEBVIEW_TITLE)

        if (mUrl.isNullOrEmpty() || mUrl.isNullOrBlank()) {
            Toast.makeText(mContext, "Url is invalid!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun initViews() {
        ib_web_back.setOnClickListener {
            finish()
        }

        tv_web_title.text = if (!TextUtils.isEmpty(mTitle)) Html.fromHtml(mTitle) else ""

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