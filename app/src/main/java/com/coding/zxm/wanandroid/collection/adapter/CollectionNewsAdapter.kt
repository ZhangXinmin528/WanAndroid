package com.coding.zxm.wanandroid.collection.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/17 . All rights reserved.
 * 我的收藏适配器
 */
class CollectionNewsAdapter(dataList: MutableList<NewsDetialEntity>) :
    BaseQuickAdapter<NewsDetialEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_collections_news_text_item,
        data = dataList
    ) {

    override fun convert(holder: BaseViewHolder, item: NewsDetialEntity) {
        holder.setText(R.id.tv_news_title, Html.fromHtml(item.title))

        holder.setText(R.id.tv_collections_time, item.niceDate)

    }
}