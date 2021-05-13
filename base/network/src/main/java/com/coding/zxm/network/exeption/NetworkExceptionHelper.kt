package com.coding.zxm.network.exeption

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.net.ssl.SSLHandshakeException

/**
 * Created by ZhangXinmin on 2020/7/26
 * Copyright (c) 2020/8/6  All rights reserved
 */
class NetworkExceptionHelper {
    companion object {
        //对应HTTP的状态码
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val REQUEST_TIMEOUT = 408
        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504

        const val RESULT_NORMAL = 0
        const val UNKNOWN = 1000
        const val PARSE_ERROR = 1001
        const val NETWORK_NOTCONNECTION = 1002
        const val UNKNOW_HOST = 1003
        const val GW_ERROR = "1004"
        const val ARGS_ERROR = "1005"
        const val TOKEN_ERROR = "1006"
        const val SIGN_ERROR = "1007"
        const val SSL_ERROR = 3001

        fun handleException(t: Throwable): NetworkException {
            val ex: NetworkException
            if (t is NetworkException) {
                ex = t
            } else if (t is HttpException) {
                ex = when (t.code()) {
                    UNAUTHORIZED,
                    FORBIDDEN,
                        //权限错误，需要实现
                    NOT_FOUND -> NetworkException(
                        t.code(),
                        "网络错误"
                    )
                    REQUEST_TIMEOUT,
                    GATEWAY_TIMEOUT -> NetworkException(
                        t.code(),
                        "网络连接超时"
                    )
                    INTERNAL_SERVER_ERROR,
                    BAD_GATEWAY,
                    SERVICE_UNAVAILABLE -> NetworkException(
                        t.code(),
                        "服务器错误"
                    )
                    else -> NetworkException(t.code(), "网络错误")
                }
            } else if (t is JsonParseException
                || t is JSONException
                || t is ParseException
            ) {
                ex = NetworkException(
                    PARSE_ERROR,
                    "解析错误"
                )
            } else if (t is SocketException) {
                ex = NetworkException(
                    REQUEST_TIMEOUT,
                    "网络连接错误，请重试"
                )
            } else if (t is SocketTimeoutException) {
                ex = NetworkException(
                    REQUEST_TIMEOUT,
                    "网络连接超时"
                )
            } else if (t is SSLHandshakeException) {
                ex = NetworkException(
                    SSL_ERROR,
                    "证书验证失败"
                )
                return ex
            } else if (t is UnknownHostException) {
                ex = NetworkException(
                    UNKNOW_HOST,
                    "网络错误，请切换网络重试"
                )
                return ex
            } else if (t is UnknownServiceException) {
                ex = NetworkException(
                    UNKNOW_HOST,
                    "网络错误，请切换网络重试"
                )
            } else if (t is NumberFormatException) {
                ex = NetworkException(
                    UNKNOW_HOST,
                    "数字格式化异常"
                )
            } else {
                ex = NetworkException(
                    UNKNOWN,
                    "未知错误"
                )
            }
            return ex
        }

    }

}