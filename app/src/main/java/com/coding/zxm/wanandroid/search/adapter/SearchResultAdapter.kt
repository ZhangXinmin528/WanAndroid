package com.coding.zxm.wanandroid.search.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.search.model.SearchDetialEntity

/**
 * Created by ZhangXinmin on 2020/8/23.
 * Copyright (c) 2020 . All rights reserved.
 */
class SearchResultAdapter(dataList: MutableList<SearchDetialEntity>) :
    BaseQuickAdapter<SearchDetialEntity, BaseViewHolder>(
        layoutResId = R.layout.layout_search_result_item,
        data = dataList
    ) {
    override fun convert(holder: BaseViewHolder, item: SearchDetialEntity) {
        holder.setText(R.id.tv_search_result_item, Html.fromHtml(item.title))
    }
}