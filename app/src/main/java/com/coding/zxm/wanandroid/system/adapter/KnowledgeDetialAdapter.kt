package com.coding.zxm.wanandroid.system.adapter

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.system.model.KnowledgeEntity
import com.google.android.flexbox.FlexboxLayout

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/28 . All rights reserved.
 */
class KnowledgeDetialAdapter(dataList: MutableList<KnowledgeEntity>) :
    BaseQuickAdapter<KnowledgeEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_knowledge_detial_item,
        data = dataList
    ) {

    private lateinit var onSystemTagClickListener: OnSystemTagClickListener

    override fun convert(holder: BaseViewHolder, item: KnowledgeEntity) {
        holder.setText(R.id.tv_knowledge_detial_title, item.name)
        val childrenList = item.children
        val childrenLayout = holder.getView<FlexboxLayout>(R.id.flex_knowledge_detial)
        childrenLayout.removeAllViews()
        if (childrenList != null && childrenList.isNotEmpty()) {
            childrenList.forEach {
                val flexItemView =
                    LayoutInflater.from(context).inflate(R.layout.layout_knowledge_flex_item, null)
                val nameView = flexItemView.findViewById<TextView>(R.id.tv_knowledge_flex_item_name)
                nameView.text = it.name
                childrenLayout.addView(flexItemView)

                flexItemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        if (onSystemTagClickListener != null) {
                            onSystemTagClickListener.onTagItemClick(flexItemView, it)
                        }
                    }

                })

            }
        }

    }

    fun setSystemTagClicklistener(tagClickListener: OnSystemTagClickListener) {
        this.onSystemTagClickListener = tagClickListener
    }

    interface OnSystemTagClickListener {
        fun onTagItemClick(view: View, knowledgeEntity: KnowledgeEntity)
    }
}