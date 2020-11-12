package com.coding.zxm.wanandroid.gallery.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.network.APIConstants
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.gallery.model.BingImageEntity
import com.example.image.model.GlideApp
import com.zxm.utils.core.screen.ScreenUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/11/12 . All rights reserved.
 */
class BingWallpapersAdapter(dataList: MutableList<BingImageEntity>) :
    BaseQuickAdapter<BingImageEntity, BaseViewHolder>(
        data = dataList,
        layoutResId = R.layout.layout_bing_list_item
    ) {

    override fun convert(holder: BaseViewHolder, item: BingImageEntity) {

        val imageView = holder.getView<ImageView>(R.id.iv_bing_item)

        val picWidth = ScreenUtil.getScreenWidth(context) - ScreenUtil.dp2px(context, 24f)

        val picHeight = picWidth * 1080 / 1920

        GlideApp.with(imageView)
            .asBitmap()
            .override(picWidth, picHeight)
            .load(APIConstants.BING_URL + item.url)
            .into(imageView)

        holder.setText(R.id.tv_picture_copyright, item.copyright)
    }
}