package com.coding.zxm.wanandroid.project.adapter

import android.text.Html
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.zxm.utils.core.time.TimeUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/9/4 . All rights reserved.
 */
class ProjectItemAdapter(private val mDataList: MutableList<NewsDetialEntity>) :
    BaseQuickAdapter<NewsDetialEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_project_list_item,
        data = mDataList
    ) {

    override fun convert(holder: BaseViewHolder, item: NewsDetialEntity) {
        holder.setText(R.id.tv_project_title, Html.fromHtml(item.title))
            .setText(R.id.tv_project_desc, item.desc)


        val author = item.author
        val shareUser = item.shareUser

        if (!TextUtils.isEmpty(author)) {
            holder.setText(R.id.tv_project_user_sort_logo, "作者")
            holder.setText(R.id.tv_project_persion_name, author)
        } else if (!TextUtils.isEmpty(shareUser)) {
            holder.setText(R.id.tv_project_user_sort_logo, "分享者")
            holder.setText(R.id.tv_project_persion_name, shareUser)
        }

        holder.setText(R.id.tv_project_time, TimeUtil.getFriendlyTimeSpanByNow(item.publishTime))
    }
}