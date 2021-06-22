package com.coding.zxm.wanandroid.system.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.system.model.NaviKnowledgeEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/27 . All rights reserved.
 */
class KnowledgeNavAdapter(val dataList: MutableList<NaviKnowledgeEntity>) :
    BaseQuickAdapter<NaviKnowledgeEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_knowledge_nav_item,
        data = dataList
    ) {

    override fun convert(holder: BaseViewHolder, item: NaviKnowledgeEntity) {

        val itemView = holder.getView<TextView>(R.id.tv_know_nav_name)

        holder.setText(R.id.tv_know_nav_name, item.naviItem.name)

        if (item.selected) {
            itemView.setBackgroundResource(R.drawable.shape_red_bg)
            itemView.setTextColor(context.resources.getColor(R.color.colorWhite))
        } else {
            itemView.background = null
            itemView.setTextColor(context.resources.getColor(R.color.color_text_black))
        }

    }
}