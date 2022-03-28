package com.coding.zxm.wanandroid.share.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity

/**
 * Created by ZhangXinmin on 2022/03/28.
 * Copyright (c) 2022/3/28 . All rights reserved.
 */
class ShareArticleAdapter(dataList: MutableList<NewsDetialEntity>) :
    BaseQuickAdapter<NewsDetialEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_share_news_text_item,
        data = dataList
    ) {
    override fun convert(holder: BaseViewHolder, item: NewsDetialEntity) {
        holder.setText(R.id.tv_news_title, Html.fromHtml(item.title))

        holder.setText(R.id.tv_share_time, item.niceDate)
    }
}