package com.coding.zxm.wanandroid.search.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.search.model.HotWordEntity

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/20 . All rights reserved.
 */
class HotWordAdapter(words: MutableList<HotWordEntity>) :
    BaseQuickAdapter<HotWordEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_search_hot_item,
        data = words
    ) {

    override fun convert(holder: BaseViewHolder, item: HotWordEntity) {

        when (item.order) {
            1 -> {
                holder.setTextColor(
                    R.id.tv_word_order,
                    context.resources.getColor(R.color.app_color_theme_1)
                )
            }
            2 -> {
                holder.setTextColor(
                    R.id.tv_word_order,
                    context.resources.getColor(R.color.app_color_theme_2)
                )
            }
            3 -> {
                holder.setTextColor(
                    R.id.tv_word_order,
                    context.resources.getColor(R.color.app_color_theme_3)
                )
            }
            else -> {
                holder.setTextColor(
                    R.id.tv_word_order,
                    context.resources.getColor(R.color.colorMainText)
                )
            }
        }
        holder.setText(R.id.tv_word_order, "${item.order}")
        holder.setText(R.id.tv_word_detial, item.name)
    }
}