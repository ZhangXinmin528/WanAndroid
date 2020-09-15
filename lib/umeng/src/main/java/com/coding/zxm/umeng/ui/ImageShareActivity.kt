package com.coding.zxm.umeng.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.umeng.R
import com.example.image.loader.ImageLoader
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import kotlinx.android.synthetic.main.activity_image_share.*
import java.io.File

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/7 . All rights reserved.
 * TODO:优化截图显示效果
 */
class ImageShareActivity : BaseActivity(), View.OnClickListener, UMShareListener {

    companion object {
        private const val PARAMS_IMAGE_PATH = "params_view"

        fun doImageShare(context: Context, path: String) {
            val intent = Intent(context, ImageShareActivity::class.java)
            intent.putExtra(PARAMS_IMAGE_PATH, path)
            context.startActivity(intent)
        }
    }

    private var mFilePath: String? = null
    private lateinit var mUmImage: UMImage
    private lateinit var mImageFile: File

    override fun setLayoutId(): Int {
        return R.layout.activity_image_share
    }

    override fun initParamsAndValues() {

        setStatusBarColorNoTranslucent()
        if (intent != null) {
            mFilePath = intent.getStringExtra(PARAMS_IMAGE_PATH)

        }

        if (TextUtils.isEmpty(mFilePath)) {
            Toast.makeText(mContext, "截图资源不存在", Toast.LENGTH_SHORT).show()
        } else {
            mImageFile = File(mFilePath)
        }

    }

    override fun initViews() {
        tv_share_cancel.setOnClickListener(this)
        tv_share_wechat.setOnClickListener(this)
        tv_share_wxcircle.setOnClickListener(this)
        tv_share_qq.setOnClickListener(this)
        tv_share_qzone.setOnClickListener(this)
        tv_share_ding.setOnClickListener(this)

        if (mImageFile.exists()) {
            val bitmap = BitmapFactory.decodeFile(mFilePath)
            mUmImage = UMImage(mContext, bitmap)
            mUmImage.compressStyle = UMImage.CompressStyle.QUALITY
            ImageLoader.INSTANCE.loadImageRes(iv_share_screenshot, mFilePath!!)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_share_cancel -> {
                finish()
            }
            R.id.tv_share_wechat -> {
                doWechatShare()
            }
            R.id.tv_share_wxcircle -> {

            }
            R.id.tv_share_qq -> {
                doQQShare()
            }
            R.id.tv_share_qzone -> {
                doQZoneShare()
            }
            R.id.tv_share_ding -> {
                doDingShare()
            }
        }
    }

    private fun doDingShare() {
        ShareAction(this)
            .setPlatform(SHARE_MEDIA.DINGTALK)
            .setCallback(this)
            .withMedia(mUmImage)
            .share()

    }

    private fun doWechatShare() {
        ShareAction(this)
            .setPlatform(SHARE_MEDIA.WEIXIN)
            .setCallback(this)
//            .withMedia(mUmImage)
            .withText("测试一下啊")
            .share()
    }

    private fun doQQShare() {
        ShareAction(this)
            .setPlatform(SHARE_MEDIA.QQ)
            .setCallback(this)
            .withMedia(mUmImage)
            .share()
    }

    private fun doQZoneShare() {
        ShareAction(this)
            .setPlatform(SHARE_MEDIA.QZONE)
            .setCallback(this)
            .withMedia(mUmImage)
            .share()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /** attention to this below ,must add this */
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        UMShareAPI.get(mContext).release()
        super.onDestroy()
    }

    override fun onResult(p0: SHARE_MEDIA?) {
        Log.d(TAG, "$p0..onResult")
        if (mImageFile.exists()) {
            mImageFile.delete()
        }
        finish()
    }

    override fun onCancel(p0: SHARE_MEDIA?) {
        Log.d(TAG, "$p0..onCancel")
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        Log.d(TAG, "$p0..onError : " + p1?.message)
        Toast.makeText(mContext, "${p0}分享失败", Toast.LENGTH_SHORT).show()
    }

    override fun onStart(p0: SHARE_MEDIA?) {
        Log.d(TAG, "$p0..onStart")
    }


}