package com.coding.zxm.wanandroid.login

import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.util.SPConfig
import com.coding.zxm.wanandroid.MainActivity
import com.coding.zxm.wanandroid.R
import com.zxm.utils.core.sp.SharedPreferencesUtil
import com.zxm.utils.core.text.ClickableMovementMethod
import com.zxm.utils.core.text.SpanUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 * For login
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    private val mLoginViewModel: LoginViewModel by viewModels { LoginViewModel.LoginViewModelFactory }

    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initParamsAndValues() {

    }

    override fun initViews() {

        toolbar_wan?.title = "登录"
//        setSupportActionBar(toolbar_wan)
//        val actionBar = supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBar?.setDisplayShowHomeEnabled(true)

        tv_login.setOnClickListener(this)

        val spannableStringBuilder =
            SpanUtils.getBuilder(mContext!!, getString(R.string.all_tips_not_register), false)
                .setTextColor(resources.getColor(R.color.colorPrimary))
                .setClickSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        //TODO:跳转到注册页面
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.color = resources.getColor(R.color.colorPrimary)
                        ds.isUnderlineText = true
                    }
                })
                .setBold()
                .append(getString(R.string.all_register), true)
                .create()

        tv_tips_register.text = spannableStringBuilder
        tv_tips_register.movementMethod = ClickableMovementMethod.getInstance()

        val userName: String =
            SharedPreferencesUtil.get(
                mContext!!,
                SPConfig.CONFIG_USER_NAME,
                ""
            ) as String
        if (!TextUtils.isEmpty(userName)) {
            et_user_name.setText(userName)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login -> {
                val userName = et_user_name.editableText.toString().trim()
                if (userName.isEmpty()) {
                    Toast.makeText(mContext!!, "请输入用户名~", Toast.LENGTH_SHORT).show()
                    return
                }

                val password = et_password.editableText.toString().trim()
                if (password.isEmpty()) {
                    Toast.makeText(mContext!!, "请输入密码~", Toast.LENGTH_SHORT).show()
                    return
                }

                mLoginViewModel.login(userName, password)
                    .observe(this, Observer {

                        SharedPreferencesUtil.put(
                            mContext!!,
                            SPConfig.CONFIG_USER_NAME,
                            it.username
                        )

                        SharedPreferencesUtil.put(
                            mContext!!,
                            SPConfig.CONFIG_STATE_LOGIN,
                            true
                        )

                        jumpActivity(MainActivity::class.java)
                        finish()
                    })
            }
        }
    }
}

