package com.coding.zxm.wanandroid.setting

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.LanguageUtil
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.MainActivity
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.databinding.ActivityLanguagesSettingBinding
import com.coding.zxm.wanandroid.setting.model.LanguageEntity
import com.zxm.utils.core.sp.SharedPreferencesUtil
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ZhangXinmin on 2020/11/29.
 * Copyright (c) 2020 . All rights reserved.
 * 语言切换页面
 */
class LanguagesSettingActivity : BaseActivity(), View.OnClickListener {

    private val mDataList: MutableList<LanguageEntity> = ArrayList()
    private lateinit var mAdapter: LanguageAdapter

    private var mLanguageEntity: LanguageEntity? = null
    private lateinit var languagesBinding: ActivityLanguagesSettingBinding

    override fun setContentLayout(): Any {
        languagesBinding = ActivityLanguagesSettingBinding.inflate(layoutInflater)
        return languagesBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarColorLight()

        val lanNameArray = resources.getStringArray(R.array.array_lan_name)
        val lanArray = resources.getStringArray(R.array.array_lan)

        for (index in lanNameArray.indices) {
            mDataList.add(LanguageEntity(lanNameArray[index], lanArray[index], index == 0))
        }

        mAdapter = LanguageAdapter(mDataList)
    }

    override fun initViews() {
        languagesBinding.toolbar.tvToolbarTitle.setText(R.string.all_setting_switch_language)

        languagesBinding.toolbar.ivToolbarBack.setOnClickListener(this)
        languagesBinding.toolbar.tvToolbarConfirm.setOnClickListener(this)
        languagesBinding.toolbar.tvToolbarConfirm.visibility = View.VISIBLE

        languagesBinding.rvLanSetting.layoutManager = LinearLayoutManager(mContext)
        languagesBinding.rvLanSetting.adapter = mAdapter
        val itemDecoration = DividerItemDecoration(
            mContext,
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(mContext!!, R.drawable.shape_list_horizontal_divider_gray)?.let {
            itemDecoration.setDrawable(
                it
            )
        }
        languagesBinding.rvLanSetting.addItemDecoration(itemDecoration)

        val position = LanguageUtil.getSettingLanguagePosition(mContext!!)
        updateLanguageList(position)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mLanguageEntity = (adapter as LanguageAdapter).getItem(position)
            updateLanguageList(position)

        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_toolbar_back -> {
                finish()
            }
            R.id.tv_toolbar_confirm -> {
                saveLanguageConfig()
                updateLanguageConfig(mLanguageEntity)
            }
        }
    }

    private fun saveLanguageConfig() {
        mLanguageEntity?.let {
            SharedPreferencesUtil.put(mContext!!, SPConfig.CONFIG_APP_LANGUAGE, it.language)
        }
    }

    private fun updateLanguageConfig(language: LanguageEntity?) {
        if (language == null)
            return

        mContext?.let {
            val config = resources.configuration
            var locale: Locale? = null
            locale = when (language.language) {
                "cn" -> {
                    Locale.SIMPLIFIED_CHINESE
                }
                "en" -> {
                    Locale.ENGLISH
                }
                else -> {
                    Locale.getDefault()
                }
            }
            if (locale != null) {
                config.locale = locale
                resources.updateConfiguration(config, resources.displayMetrics)
            }

            finish()

            val intent = Intent(it, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun updateLanguageList(position: Int) {
        mDataList.forEachIndexed { index, languageEntity ->
            languageEntity.checked = position == index
        }
        mAdapter.notifyDataSetChanged()
    }
}


private class LanguageAdapter(dataList: MutableList<LanguageEntity>) :
    BaseQuickAdapter<LanguageEntity, BaseViewHolder>(
        data = dataList,
        layoutResId = R.layout.layout_languages_setting_list_item
    ) {

    override fun convert(holder: BaseViewHolder, item: LanguageEntity) {
        holder.setText(R.id.tv_lan_name, item.name)
        if (item.checked) {
            holder.setImageResource(R.id.iv_lan_checked, R.mipmap.icon_checked)
        } else {
            holder.setImageResource(R.id.iv_lan_checked, 0)
        }
    }

}