package com.coding.zxm.wanandroid.login

import com.coding.zxm.network.BaseRepository
import com.coding.zxm.network.RetrofitClient
import com.coding.zxm.network.callback.NetworkResult

/**
 * Created by ZhangXinmin on 2020/8/2.
 * Copyright (c) 2020 . All rights reserved.
 */
class LoginRepository(private val client: RetrofitClient) : BaseRepository() {

    /**
     * User login
     */
    suspend fun login(userName: String, password: String): NetworkResult<UserEntity> {
        return onRequest(call = { requestLogin(userName, password) })
    }

    private suspend fun requestLogin(
        userName: String,
        password: String
    ): NetworkResult<UserEntity> {
        return onResponse(client.create(LoginService::class.java).login(userName, password))
    }

    /**
     * User register
     */
    suspend fun register(
        userName: String,
        password: String,
        repassword: String
    ): NetworkResult<UserEntity> {
        return onRequest(call = { requestRegister(userName, password, repassword) })
    }

    private suspend fun requestRegister(
        userName: String,
        password: String,
        repassword: String
    ): NetworkResult<UserEntity> {
        return onResponse(
            client.create(LoginService::class.java).register(userName, password, repassword)
        )
    }
}