package com.coding.zxm.wanandroid.navigation

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.home.model.NewsDetialEntity
import com.coding.zxm.wanandroid.navigation.model.NaviEntity
import com.google.android.flexbox.FlexboxLayout

/**
 * Created by ZhangXinmin on 2020/8/30.
 * Copyright (c) 2020 . All rights reserved.
 */
class NavigationAdapter(dataList: MutableList<NaviEntity>) :
    BaseQuickAdapter<NaviEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_navigation_detial_item,
        data = dataList
    ) {

    private lateinit var naviTagClickListener: OnNaviTagClickListener

    override fun convert(holder: BaseViewHolder, item: NaviEntity) {
        holder.setText(R.id.tv_navi_detial_title, item.name)
        val flexboxLayout = holder.getView<FlexboxLayout>(R.id.flex_navi_detial)

        val articles = item.articles
        if (articles != null && articles.isNotEmpty()) {
            flexboxLayout.removeAllViews()
            articles.forEach {
                val flexItemView =
                    LayoutInflater.from(context).inflate(R.layout.layout_knowledge_flex_item, null)
                val nameView = flexItemView.findViewById<TextView>(R.id.tv_knowledge_flex_item_name)
                nameView.text = it.title
                val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(10, 10, 10, 10)
                nameView.layoutParams = layoutParams
                flexboxLayout.addView(flexItemView)

                flexItemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        if (naviTagClickListener != null) {
                            naviTagClickListener.onTagItemClick(flexItemView, it)
                        }
                    }

                })
            }
        }

    }

    fun setNaviTagClicklistener(tagClickListener: OnNaviTagClickListener) {
        this.naviTagClickListener = tagClickListener
    }

    interface OnNaviTagClickListener {
        fun onTagItemClick(view: View, newsEntity: NewsDetialEntity)
    }
}