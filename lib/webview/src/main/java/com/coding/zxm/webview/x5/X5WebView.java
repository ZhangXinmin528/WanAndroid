package com.coding.zxm.webview.x5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.coding.zxm.webview.R;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by ZhangXinmin on 2018/12/7.
 * Copyright (c) 2018 . All rights reserved.
 */
public class X5WebView extends WebView {
    private static final String TAG = X5WebView.class.getSimpleName();

    private ProgressBar progressbar;  //进度条

    private WebViewListener webViewListener;
    //处理各种事件
    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);//当前页面打开
            return true;
        }

        //页面加载完成时的操作
        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
            if (webViewListener != null) {
                webViewListener.onPageFinish(webView);
            }
        }

        //webview报告错误信息
        @Override
        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            Log.e(TAG, "onReceivedError:" + errorCode + description + failingUrl);
        }

        //webview 处理http信息
        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }

    };
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int progress) {
            if (progress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (!progressbar.isShown())
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(progress);
            }

            if (webViewListener != null) {
                webViewListener.onProgressChange(webView, progress);
            }
            super.onProgressChanged(webView, progress);
        }

        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult jsResult) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getContext().getString(R.string.app_name));
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);
            builder.create().show();
            return true;
        }

    };

    public X5WebView(Context context) {
        this(context, null, 0);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);

        initParams();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initParams() {
        WebSettings webSetting = getSettings();

        //创建进度条
        progressbar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, 10, 0, 0));//设置高度
        Drawable drawable = getResources().getDrawable(R.drawable.color_progressbar);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);//添加进度条

        webSetting.setJavaScriptEnabled(true);//支持JS脚本
        //适配手机大小
        webSetting.setUseWideViewPort(true);//调整图片适合屏幕
        webSetting.setLoadWithOverviewMode(true);//缩放至屏幕大小
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//支持内容重新布局，调用该方法会引起页面重绘
        webSetting.setSupportZoom(true);//支持缩放
        webSetting.setBuiltInZoomControls(true);//设置内置的缩放控件。
        webSetting.setDisplayZoomControls(false);//隐藏原生的缩放控件
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheEnabled(false);//设置缓存
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        setWebChromeClient(webChromeClient);

        setWebViewClient(webViewClient);

        webViewListener = new WebViewListener() {
            @Override
            public void onProgressChange(WebView view, int newProgress) {

            }

            @Override
            public void onPageFinish(WebView view) {

            }
        };
    }

    /**
     * 设置回调接口
     *
     * @param webViewListener
     */
    public void setWebViewListener(WebViewListener webViewListener) {
        this.webViewListener = webViewListener;
    }

    //回调接口
    public interface WebViewListener {
        void onProgressChange(WebView view, int newProgress);

        void onPageFinish(WebView view);
    }
}
