package com.coding.zxm.upgrade

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseActivity
import com.zxm.utils.core.log.MLogger
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by ZhangXinmin on 2021/05/14.
 * Copyright (c) 5/14/21 . All rights reserved.
 */
class TestActivty : BaseActivity() {

    private val mViewModel: UpgradeViewModel by viewModels { UpgradeViewModel.LoginViewModelFactory }

    override fun setLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initParamsAndValues() {

    }

    override fun initViews() {
        upgrade.setOnClickListener {
            mViewModel.checkUpgrade().observe(this, Observer {
                MLogger.d(it)
            })
        }
    }
}