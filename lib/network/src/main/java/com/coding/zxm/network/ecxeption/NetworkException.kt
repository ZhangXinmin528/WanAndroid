package com.coding.zxm.network.ecxeption

/**
 * Created by ZhangXinmin on 2020/7/26.
 * Copyright (c) 2020/7/31 . All rights reserved.
 */
class NetworkException(val errorCode: Int, val errorMsg: String) : Exception()