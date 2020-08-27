package com.coding.zxm.wanandroid.system.adapter

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

        holder.setText(R.id.tv_know_nav_name, item.naviItem.name)
        if (item.selected) {
            holder.setBackgroundColor(R.id.tv_know_nav_name, R.color.colorWhite)
        } else {
            holder.setBackgroundColor(R.id.tv_know_nav_name, R.color.color_main_bg)
        }

    }
}