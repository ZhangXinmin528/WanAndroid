package com.coding.zxm.wanandroid.login

import android.app.Activity
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
import com.coding.zxm.wanandroid.databinding.ActivityLoginBinding
import com.zxm.utils.core.sp.SharedPreferencesUtil
import com.zxm.utils.core.text.ClickableMovementMethod
import com.zxm.utils.core.text.SpanUtils

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 * For login
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var loginBinding: ActivityLoginBinding

    private val mLoginViewModel: LoginViewModel by viewModels { LoginViewModel.LoginViewModelFactory }
    override fun setContentLayout(): Any {
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        return loginBinding.root
    }

    override fun initParamsAndValues() {
        setStatusBarDark()
    }

    override fun initViews() {

        loginBinding.tvLogin.setOnClickListener(this)
        loginBinding.ivLoginClose.setOnClickListener(this)

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

        loginBinding.tvTipsRegister.text = spannableStringBuilder
        loginBinding.tvTipsRegister.movementMethod = ClickableMovementMethod.getInstance()

        val userName: String =
            SharedPreferencesUtil.get(
                mContext!!,
                SPConfig.CONFIG_USER_NAME,
                ""
            ) as String
        if (!TextUtils.isEmpty(userName)) {
            loginBinding.etUserName.setText(userName)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login -> {
                val userName = loginBinding.etUserName.editableText.toString().trim()
                if (userName.isEmpty()) {
                    Toast.makeText(mContext!!, "请输入用户名~", Toast.LENGTH_SHORT).show()
                    return
                }

                val password = loginBinding.etPassword.editableText.toString().trim()
                if (password.isEmpty()) {
                    Toast.makeText(mContext!!, "请输入密码~", Toast.LENGTH_SHORT).show()
                    return
                }

                mLoginViewModel.login(userName, password)
                    .observe(this, Observer {
                        if (it != null) {
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

                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    })
            }
            R.id.iv_login_close -> {
                jumpActivity(MainActivity::class.java)
                finish()
            }
        }
    }
}

