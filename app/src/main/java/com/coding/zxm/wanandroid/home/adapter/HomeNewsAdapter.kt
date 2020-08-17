package com.coding.zxm.wanandroid.home.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.zxm.utils.core.time.TimeUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/17 . All rights reserved.
 */
class HomeNewsAdapter(dataList: MutableList<NewsDetialEntity>) :
    BaseQuickAdapter<NewsDetialEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_home_news_text_item,
        data = dataList
    ) {

    override fun convert(holder: BaseViewHolder, item: NewsDetialEntity) {
        holder.setText(R.id.tv_news_title, item.title)

        val author = item.author
        val shareUser = item.shareUser

        if (!TextUtils.isEmpty(author)) {
            holder.setText(R.id.tv_user_sort_logo, "作者")
            holder.setText(R.id.tv_persion_name, author)
        } else if (!TextUtils.isEmpty(shareUser)) {
            holder.setText(R.id.tv_user_sort_logo, "分享者")
            holder.setText(R.id.tv_persion_name, shareUser)
        }

        holder.setText(R.id.tv_news_time, TimeUtil.getFriendlyTimeSpanByNow(item.publishTime))

    }
}