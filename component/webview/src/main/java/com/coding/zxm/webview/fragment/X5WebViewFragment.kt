package com.coding.zxm.webview.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.coding.zxm.webview.x5.X5WebView
import com.coding.zxm.webview.x5.X5WebView.WebViewListener
import com.tencent.smtt.sdk.WebView

/**
 * Created by ZhangXinmin on 2018/12/7.
 * Copyright (c) 2018 . All rights reserved.
 *
 *
 * A fragment that displays a WebView[X5WebView].
 * The WebView[X5WebView] is automically paused or resumed when the Fragment is paused or resumed.
 *
 */
class X5WebViewFragment : Fragment(), WebViewListener {
    private var mContext: Context? = null
    private var mWebView: X5WebView? = null
    private var mIsWebViewAvailable = false
    private var mUrl: String? = null
    private var mIsPageFinished = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParamsAndValus()

    }

    private fun initParamsAndValus() {
        val args = arguments
        if (args != null) {
            if (args.containsKey(PARAMS_WEBVIEW_URL)) {
                mUrl = args.getString(PARAMS_WEBVIEW_URL)
            }
        }
        if (TextUtils.isEmpty(mUrl)) {
            val activity: Activity? = activity
            if (activity != null) {
                val fm =
                    (activity as FragmentActivity).supportFragmentManager
                fm.popBackStack()
            }
        }
        mIsPageFinished = false
    }

    /**
     * Called to instantiate the view. Creates and returns the WebView.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mWebView != null) {
            mWebView!!.destroy()
        }
        mWebView = X5WebView(context)
        mWebView!!.setWebViewListener(this)
        mIsWebViewAvailable = true
        return mWebView
    }

    /**
     * 处理回退事件
     */
    fun onKeyDown() {
        if (mWebView != null && mWebView!!.canGoBack()) {
            mWebView!!.goBack()
        }
    }

    override fun onStart() {
        super.onStart()
        if (mIsWebViewAvailable && mWebView != null && !TextUtils.isEmpty(mUrl)
        ) {
            mWebView!!.loadUrl(mUrl)
        }
    }

    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    override fun onPause() {
        super.onPause()
        mWebView!!.onPause()
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    override fun onResume() {
        mWebView!!.onResume()
        super.onResume()
    }


    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    override fun onDestroyView() {
        mIsWebViewAvailable = false
        super.onDestroyView()
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView[X5WebView].
     */
    override fun onDestroy() {
        if (mWebView != null) {
            mWebView!!.destroy()
            mWebView = null
        }
        super.onDestroy()
    }

    /**
     * Gets the WebView[X5WebView].
     */
    val webView: X5WebView?
        get() = if (mIsWebViewAvailable) mWebView else null

    override fun onProgressChange(
        view: WebView,
        newProgress: Int
    ) {
    }

    override fun onPageFinish(view: WebView) {
        mIsPageFinished = true
    }

    companion object {
        const val PARAMS_WEBVIEW_URL = "webview_url"
        const val PARAMS_WEBVIEW_TITLE = "webview_title"
        private val TAG = X5WebViewFragment::class.java.simpleName
        fun newInstance(url: String): X5WebViewFragment {
            val fragment = X5WebViewFragment()
            val args = Bundle()
            if (!TextUtils.isEmpty(url)) {
                args.putString(PARAMS_WEBVIEW_URL, url)
            }
            fragment.arguments = args
            return fragment
        }
    }
}