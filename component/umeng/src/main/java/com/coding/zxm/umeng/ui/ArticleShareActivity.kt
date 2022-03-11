package com.coding.zxm.umeng.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.lib.qrcode.QrCodeUtils
import com.coding.zxm.umeng.R
import com.coding.zxm.umeng.databinding.ActivityImageShareBinding
import com.coding.zxm.umeng.model.ArticleEntity
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.zxm.utils.core.image.ImageUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/7 . All rights reserved.
 * 文章分享
 */
class ArticleShareActivity : BaseActivity(), View.OnClickListener, UMShareListener {

    companion object {
        private const val PARAMS_ARTICLE_DATA = "params_article"

        fun doArticleShare(context: Context, data: String) {
            val intent = Intent(context, ArticleShareActivity::class.java)
            val args = Bundle()
            args.putString(PARAMS_ARTICLE_DATA, data)
            intent.putExtras(args)
            context.startActivity(intent)
        }
    }

    private var mArticleEntity: ArticleEntity? = null
    private var mUmImage: UMImage? = null
    private lateinit var shareBinding: ActivityImageShareBinding

    override fun setContentLayout(): Any {
        shareBinding = ActivityImageShareBinding.inflate(layoutInflater)
        return shareBinding.root
    }

    override fun initParamsAndValues() {
        val args = intent.extras
        if (args != null) {
            if (args.containsKey(PARAMS_ARTICLE_DATA)) {
                val data = args.getString(PARAMS_ARTICLE_DATA)
                if (!TextUtils.isEmpty(data)) {
                    mArticleEntity = JSON.parseObject(data, ArticleEntity::class.java)
                }
            }
        }
    }

    override fun initViews() {
        shareBinding.tvShareCancel.setOnClickListener(this)
        shareBinding.tvNewsTitle.text = mArticleEntity?.title
        shareBinding.tvNewsNiceDate.text = mArticleEntity?.niceDate
//        tv_share_wechat.setOnClickListener(this)
//        tv_share_wxcircle.setOnClickListener(this)
        shareBinding.logoQq.setOnClickListener(this)
        shareBinding.logoQzone.setOnClickListener(this)
        shareBinding.logoDingding.setOnClickListener(this)
        if (mArticleEntity != null && !TextUtils.isEmpty(mArticleEntity!!.link)) {
//            val logoBm = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

            val qrBitmap = QrCodeUtils.generateQRCode(mContext!!, 100f, mArticleEntity?.link!!)
            shareBinding.ivShotQrcode.setImageBitmap(qrBitmap)

            shareBinding.layoutNewsShot.viewTreeObserver.addOnGlobalLayoutListener {
                val shotBitmap = ImageUtil.view2Bitmap(shareBinding.layoutNewsShot)

                mUmImage = UMImage(mContext, shotBitmap)
                mUmImage?.compressStyle = UMImage.CompressStyle.QUALITY
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_share_cancel -> {
                finish()
            }
//            R.id.tv_share_wechat -> {
//                doWechatShare()
//            }
//            R.id.tv_share_wxcircle -> {
//
//            }
            R.id.logo_qq -> {
                doQQShare()
            }
            R.id.logo_qzone -> {
                doQZoneShare()
            }
            R.id.logo_dingding -> {
                doDingShare()
            }
        }
    }

    private fun doDingShare() {
        if (mUmImage != null) {
            ShareAction(this)
                .setPlatform(SHARE_MEDIA.DINGTALK)
                .setCallback(this)
                .withMedia(mUmImage)
                .share()
        }
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
        if (mUmImage != null) {
            ShareAction(this)
                .setPlatform(SHARE_MEDIA.QQ)
                .setCallback(this)
                .withMedia(mUmImage)
                .share()
        }
    }

    private fun doQZoneShare() {
        if (mUmImage != null) {
            ShareAction(this)
                .setPlatform(SHARE_MEDIA.QZONE)
                .setCallback(this)
                .withMedia(mUmImage)
                .share()
        }
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
        Log.d(sTAG, "$p0..onResult")
        finish()
    }

    override fun onCancel(p0: SHARE_MEDIA?) {
        Log.d(sTAG, "$p0..onCancel")
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        Log.d(sTAG, "$p0..onError : " + p1?.message)
        Toast.makeText(mContext, "${p0}分享失败", Toast.LENGTH_SHORT).show()
    }

    override fun onStart(p0: SHARE_MEDIA?) {
        Log.d(sTAG, "$p0..onStart")
    }


}