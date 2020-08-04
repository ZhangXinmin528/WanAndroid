package com.coding.zxm.wanandroid.login

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.coding.zxm.core.base.BaseActivity
import com.coding.zxm.wanandroid.R
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

    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.LoginViewModelFactory }

    override fun setLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initParamsAndValues() {

    }

    override fun initViews() {

        toolbar_wan?.title = "登录"
        setSupportActionBar(toolbar_wan)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        tv_login.setOnClickListener(this)

        val spannableStringBuilder =
            SpanUtils.getBuilder(mContext!!, getString(R.string.all_tips_not_register), false)
                .setTextColor(Color.parseColor("#298EFB"))
                .setClickSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        //TODO:跳转到注册页面
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.color = Color.parseColor("#298EFB")
                        ds.isUnderlineText = true
                    }
                })
                .append(getString(R.string.all_register), true)
                .create()

        tv_tips_register.text = spannableStringBuilder
        tv_tips_register.movementMethod = ClickableMovementMethod.getInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_login -> {
                val userName = et_user_name.editableText.toString().trim()
                if (userName.isEmpty()) {
                    Toast.makeText(mContext!!, "请输入用户名~", Toast.LENGTH_SHORT).show()
                }
                val password = et_password.editableText.toString().trim()
                if (password.isEmpty()) {
                    Toast.makeText(mContext!!, "请输入密码~", Toast.LENGTH_SHORT).show()
                }
                loginViewModel.login(userName, password)
            }
        }

    }
}

