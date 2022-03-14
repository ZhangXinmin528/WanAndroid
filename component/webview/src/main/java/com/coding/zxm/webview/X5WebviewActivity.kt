package com.coding.zxm.webview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.core.base.constants.RoutePath
import com.coding.zxm.umeng.model.ArticleEntity
import com.coding.zxm.umeng.ui.ArticleShareActivity
import com.coding.zxm.webview.databinding.ActivityWebviewBinding
import com.coding.zxm.webview.fragment.X5WebViewFragment.Companion.PARAMS_WEBVIEW_TITLE
import com.coding.zxm.webview.fragment.X5WebViewFragment.Companion.PARAMS_WEBVIEW_URL
import com.coding.zxm.webview.x5.X5WebView
import com.tencent.smtt.sdk.WebView
import com.zxm.utils.core.image.ImageUtil
import java.io.File

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
@Route(path = RoutePath.ROUTE_PATH_WEBVIEW)
class X5WebviewActivity : BaseActivity(), X5WebView.WebViewListener, View.OnClickListener {

    private val mViewModel: CollectionViewModel by viewModels { CollectionViewModel.CollectionViewModelFactory }

    private var mIsWebViewAvailable = false
    private var mIsPageFinished = false

    private var mUrl: String? = null
    private var mTitle: String? = null
    private var mJsonData: String? = ""
    private var mIsBanner: Boolean = false
    private var mCollect: Boolean = false
    private var mArticleEntity: ArticleEntity? = null


    companion object {
        const val PARAMS_WEBVIEW_DATA = "webview_data"
        const val PARAMS_WEBVIEW_BANNER = "webview_isbanner"
        const val PARAMS_WEBVIEW_COLLECT = "webview_data_collect"
        private var mCallback: OnCollectionChangedListener? = null

        fun loadUrl(
            context: Context,
            title: String?,
            url: String,
            isBanner: Boolean
        ) {
            val intent = Intent(context, X5WebviewActivity::class.java)
            val args = Bundle()
            args.putString(PARAMS_WEBVIEW_URL, url)
            args.putString(PARAMS_WEBVIEW_TITLE, title)
            args.putBoolean(PARAMS_WEBVIEW_BANNER, isBanner)
            intent.putExtras(args)
            context.startActivity(intent)
        }

        fun loadUrl(
            context: Context,
            title: String?,
            url: String,
            jsonData: String? = "",
            collect: Boolean = false,
            callback: OnCollectionChangedListener
        ) {
            mCallback = callback
            val intent = Intent(context, X5WebviewActivity::class.java)
            val args = Bundle()
            args.putString(PARAMS_WEBVIEW_URL, url)
            args.putString(PARAMS_WEBVIEW_TITLE, title)
            args.putString(PARAMS_WEBVIEW_DATA, jsonData)
            args.putBoolean(PARAMS_WEBVIEW_COLLECT, collect)
            intent.putExtras(args)
            context.startActivity(intent)

        }
    }

    private lateinit var x5Binding: ActivityWebviewBinding

    override fun setContentLayout(): Any {
        x5Binding = ActivityWebviewBinding.inflate(layoutInflater)
        return x5Binding.root
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()

        val args = intent.extras
        if (args != null) {
            if (args.containsKey(PARAMS_WEBVIEW_URL)) {
                mUrl = args.getString(PARAMS_WEBVIEW_URL)
            }
            if (args.containsKey(PARAMS_WEBVIEW_TITLE)) {
                mTitle = args.getString(PARAMS_WEBVIEW_TITLE)
            }
            if (args.containsKey(PARAMS_WEBVIEW_DATA)) {
                mJsonData = args.getString(PARAMS_WEBVIEW_DATA)
                if (!TextUtils.isEmpty(mJsonData)) {
                    mArticleEntity = JSONObject.parseObject(mJsonData, ArticleEntity::class.java)
                }
            }
            if (args.containsKey(PARAMS_WEBVIEW_BANNER)) {
                mIsBanner = args.getBoolean(PARAMS_WEBVIEW_BANNER)
            }
            if (args.containsKey(PARAMS_WEBVIEW_COLLECT)) {
                mCollect = args.getBoolean(PARAMS_WEBVIEW_COLLECT)
            }
        }

        if (mUrl.isNullOrEmpty() || mUrl.isNullOrBlank()) {
            Toast.makeText(mContext, "Url is invalid!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun initViews() {
        x5Binding.ivWebBack.setOnClickListener(this)

//        tv_web_title.text = if (!TextUtils.isEmpty(mTitle)) Html.fromHtml(mTitle) else ""

        x5Binding.x5webview.setWebViewListener(this)
        mIsWebViewAvailable = true

        x5Binding.ivWebMore.setOnClickListener(this)

        if (!mIsBanner) {
            x5Binding.layoutBottomFunc.visibility = View.VISIBLE
            if (mCollect) {
                x5Binding.ivCollect.setImageResource(R.drawable.icon_collected)
            } else {
                x5Binding.ivCollect.setImageResource(R.drawable.icon_not_collected)
            }
        } else {
            x5Binding.layoutBottomFunc.visibility = View.GONE
        }

        x5Binding.layoutCollect.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        if (mIsWebViewAvailable && !TextUtils.isEmpty(mUrl)
        ) {
            x5Binding.x5webview.loadUrl(mUrl)
        }
    }

    override fun onPause() {
        x5Binding.x5webview.onPause()
        super.onPause()
    }

    override fun onResume() {
        x5Binding.x5webview.onResume()
        super.onResume()
    }


    override fun onProgressChange(view: WebView?, newProgress: Int) {

    }

    override fun onPageFinish(view: WebView?) {
        mIsPageFinished = true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_web_back -> {
                finish()
            }
            R.id.iv_web_more -> {
                shareArticle()
            }
            R.id.layout_collect -> {
                if (mArticleEntity == null)
                    return
                val id = mArticleEntity?.id
                if (!mCollect) {
                    id?.let { collect(it) }
                } else {
                    id?.let { uncollect(it) }
                }
            }
        }
    }

    private fun collect(id: String) {
        mViewModel.collect(id).observe(this, Observer {
            if (it == 0) {
                Toast.makeText(
                    mContext,
                    resources.getString(R.string.all_tips_collection_success),
                    Toast.LENGTH_SHORT
                ).show()
                x5Binding.ivCollect.setImageResource(R.drawable.icon_collected)
                mCollect = true
                mCallback?.collectionChanged()
            }
        })
    }

    private fun uncollect(id: String) {
        mViewModel.uncollect(id).observe(this, Observer {
            if (it == 0) {
                Toast.makeText(
                    mContext,
                    resources.getString(R.string.all_tips_uncollection_success),
                    Toast.LENGTH_SHORT
                ).show()
                x5Binding.ivCollect.setImageResource(R.drawable.icon_not_collected)
                mCollect = false
                mCallback?.collectionChanged()
            }
        })
    }

    private fun shareArticle() {
        if (!TextUtils.isEmpty(mJsonData)) {
            ArticleShareActivity.doArticleShare(mContext!!, mJsonData!!)
        }
    }

    /**
     * 屏幕截图
     */
    @Deprecated(message = "其他方案替代")
    private fun shareScreenShot() {

        val bitmap = ImageUtil.view2Bitmap(x5Binding.x5webview)
        val filePath =
            filesDir.absolutePath + File.separator + "share" + File.separator + "share_image.png"
        val state = ImageUtil.save(bitmap, filePath, Bitmap.CompressFormat.PNG)

        Log.d(sTAG, "save state $state .. file path $filePath")

        if (state) {
            ArticleShareActivity.doArticleShare(mContext!!, filePath)
        }

    }

    /**
     * 截取屏幕长图
     * 存在问题，不建议使用
     */
    @Deprecated(message = "存在问题")
    private fun longScreenShot() {
        val wholeWidth: Int = x5Binding.x5webview.computeHorizontalScrollRange()
        var wholeHeight: Int = x5Binding.x5webview.computeVerticalScrollRange()
        wholeHeight /= 2 //高度截取一半，防止oom，后面可以指定高度，缩放进行换算

        val x5bitmap = Bitmap.createBitmap(wholeWidth, wholeHeight, Bitmap.Config.RGB_565)

        val x5canvas = Canvas(x5bitmap)
        x5canvas.scale(
            wholeWidth.toFloat() / x5Binding.x5webview.contentWidth.toFloat(),
            wholeHeight.toFloat() / (x5Binding.x5webview.contentHeight / 2).toFloat()
        )

        val x5WebViewExtension = x5Binding.x5webview.x5WebViewExtension
        x5WebViewExtension?.snapshotWholePage(x5canvas, false, false, Runnable {
            val filePath =
                filesDir.absolutePath + File.separator + "share" + File.separator + "share_image.png"
            val state = ImageUtil.save(x5bitmap, filePath, Bitmap.CompressFormat.PNG)

            Log.d(sTAG, "save state $state .. file path $filePath")

            if (state) {
                ArticleShareActivity.doArticleShare(mContext!!, filePath)
            }
        })

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && x5Binding.x5webview.canGoBack()) {
            x5Binding.x5webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        x5Binding.x5webview.destroy()
        super.onDestroy()
    }

}

interface OnCollectionChangedListener {
    fun collectionChanged()
}