package com.coding.zxm.wanandroid.mine

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.BaseStatusBarFragment
import com.coding.zxm.wanandroid.R
import com.coding.zxm.wanandroid.collection.CollectionsActivity
import com.coding.zxm.wanandroid.databinding.FragmentMineBinding
import com.coding.zxm.wanandroid.login.LoginActivity
import com.coding.zxm.wanandroid.navigation.NavigationActivity
import com.coding.zxm.wanandroid.project.ProjectActivity
import com.coding.zxm.wanandroid.setting.SettingActivity
import com.coding.zxm.wanandroid.share.SharedActivity
import com.coding.zxm.wanandroid.system.KnowledgeActivity
import com.coding.zxm.wanandroid.ui.activity.AboutActivity
import com.coding.zxm.wanandroid.util.ToastUtil
import com.zxm.utils.core.bar.StatusBarCompat
import com.zxm.utils.core.sp.SharedPreferencesUtil

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/8/24 . All rights reserved.
 */
class MineFragment() : BaseStatusBarFragment(), View.OnClickListener {

    private val mMineViewModel: MineViewModel by viewModels { MineViewModel.MineViewModelFactory }

    companion object {

        private const val REQUEST_CODE_LOGIN = 1001

        fun newInstance(): MineFragment {
            return MineFragment()
        }

    }

    private lateinit var mineBinding: FragmentMineBinding

    override fun setContentLayout(container: ViewGroup?): View {
        mineBinding = FragmentMineBinding.inflate(layoutInflater, container, false)
        return mineBinding.root
    }

    override fun initParamsAndValues() {
    }

    override fun initViews() {
        val layoutParams = mineBinding.fakeStatusBar.fakeStatusBar.layoutParams
        layoutParams.height = StatusBarCompat.getStatusBarHeight(mContext!!)
        mineBinding.fakeStatusBar.fakeStatusBar.layoutParams = layoutParams

        mineBinding.ivUser.setImageResource(R.mipmap.icon_splash)

        mineBinding.tvUserName.setOnClickListener(this)
        mineBinding.tvMineSystem.setOnClickListener(this)
        mineBinding.tvMineNavigation.setOnClickListener(this)
        mineBinding.tvMineAbout.setOnClickListener(this)
        mineBinding.tvMineProject.setOnClickListener(this)
        mineBinding.ivMineSetting.setOnClickListener(this)
        mineBinding.tvMineCollection.setOnClickListener(this)
        mineBinding.tvMineShare.setOnClickListener(this)
        mineBinding.tvMineTodo.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        getUserInfo()
    }

    private fun getUserInfo() {
        val loginState = SharedPreferencesUtil.get(
            mContext!!,
            SPConfig.CONFIG_STATE_LOGIN,
            false
        ) as Boolean

        if (loginState) {
            val userLiveData = mMineViewModel.getUserInfo()
            userLiveData.observe(this, Observer {
                if (it == null)
                    return@Observer

                val userDetialEntity = userLiveData.value
                userDetialEntity?.let {
                    val userName = SharedPreferencesUtil.get(
                        mContext!!,
                        SPConfig.CONFIG_USER_NAME,
                        ""
                    ) as String

                    mineBinding.tvUserName.text = userName
                    mineBinding.tvUserCoin.text =
                        getString(R.string.all_coin_count, userDetialEntity.coinCount.toString())
                    mineBinding.tvUserLevel.text =
                        getString(R.string.all_coin_level, userDetialEntity.level.toString())
                }
            })
        } else {
            mineBinding.tvUserName.text = getString(R.string.all_not_login)
            mineBinding.tvUserCoin.text = getString(R.string.all_coin_count, "--")
            mineBinding.tvUserLevel.text = getString(R.string.all_coin_level, "--")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_user_name -> {
                val intent = Intent(mContext!!, LoginActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE_LOGIN)
            }
            R.id.tv_mine_system -> {
                val intent = Intent(mContext!!, KnowledgeActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_navigation -> {
                val intent = Intent(mContext!!, NavigationActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_about -> {
                val intent = Intent(mContext!!, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_mine_project -> {
                val intent = Intent(mContext!!, ProjectActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_mine_setting -> {
                val setting = Intent(mContext!!, SettingActivity::class.java)
                startActivity(setting)
            }
            R.id.tv_mine_collection -> {
                val collections = Intent(mContext!!, CollectionsActivity::class.java)
                startActivity(collections)
            }
            R.id.tv_mine_share -> {
                val share = Intent(mContext!!, SharedActivity::class.java)
                startActivity(share)
            }
            R.id.tv_mine_todo -> {
                ToastUtil.showToast("开发小伙伴正紧张开发中")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_LOGIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    getUserInfo()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}