package com.coding.zxm.wanandroid.login

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.common.CommonResult

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 */
class LoginRepository(client: RetrofitClient) : BaseRepository(client = client) {

    /**
     * User login
     */
    suspend fun login(userName: String, password: String): CommonResult<UserEntity> {
        return onCall { onLogin(userName, password) }
    }

    private suspend fun onLogin(userName: String, password: String): CommonResult<UserEntity> {
        return excuteResponse(creatService(LoginService::class.java).login(userName, password))
    }

    /**
     * User register
     */
    suspend fun register(
        userName: String,
        password: String,
        repassword: String
    ): CommonResult<UserEntity> {
        return onCall { onRegister(userName, password, repassword) }
    }

    private suspend fun onRegister(
        userName: String,
        password: String,
        repassword: String
    ): CommonResult<UserEntity> {
        return excuteResponse(
            creatService(LoginService::class.java).register(
                userName,
                password,
                repassword
            )
        )
    }

}