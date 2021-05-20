package com.coding.zxm.upgrade.provider

import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.coding.zxm.upgrade.SSLSocketFactoryCompat
import com.coding.zxm.upgrade.UpdateDialogFragment
import com.coding.zxm.upgrade.UpgradeAPI
import com.coding.zxm.upgrade.callback.FileDownloadCallback
import com.coding.zxm.upgrade.entity.UpdateEntity
import com.coding.zxm.upgrade.utils.UpgradeUtils
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

/**
 * Created by ZhangXinmin on 2021/05/20.
 * Copyright (c) 5/20/21 . All rights reserved.
 */
class UpgradeProgressProvider(val activity: FragmentActivity) :
    IUpgradeProvider {

    companion object {
        private const val TAG = "DownloadProvider"
        private var okHttpClient: OkHttpClient? = null
        private var sslSocketFactory: SSLSocketFactoryCompat? = null
        private var loggingInterceptor: HttpLoggingInterceptor? = null
        private var apkName: String = ""
        private var apkFile: File? = null
    }

    init {
        init()
    }

    private fun init() {
        val trustAllCert = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf<X509Certificate>()
            }

        }

        sslSocketFactory =
            SSLSocketFactoryCompat(trustAllCert)
        loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, "checkUpgrade()..msg:$message")
        }

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .sslSocketFactory(sslSocketFactory, trustAllCert)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    override fun checkUpgrade(@NonNull token: String, name: String): Boolean {

        if (okHttpClient == null) {
            Log.e(TAG, "OkHttpClient初始化失败")
            return false
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.bq04.com/apps/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient!!)
            .build()

        retrofit.create(UpgradeAPI::class.java).checkUpdate(token)
            .enqueue(object : Callback<UpdateEntity> {
                override fun onFailure(call: Call<UpdateEntity>, t: Throwable) {
                    //TODO:检查更新失败
                }

                override fun onResponse(
                    call: Call<UpdateEntity>,
                    response: Response<UpdateEntity>
                ) {
                    if (response.isSuccessful) {
                        val entity = response.body()
                        if (hasNewVersion(entity)) {
                            apkName = name + "_" + entity?.versionShort + ".apk"

                            showUpgradeDialog(entity)
                        } else {
                            Toast.makeText(activity, "已是最新版本", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        //TODO:检查更新失败

                    }
                }

            })
        return true
    }

    override fun downloadApk(downloadUrl: String?, callback: FileDownloadCallback?): Boolean {
        if (!TextUtils.isDigitsOnly(downloadUrl)) {

            if (okHttpClient == null) {
                Log.e(TAG, "OkHttpClient初始化失败")
                callback?.onDownloadFailed("OkHttpClient初始化失败")
                return false
            }

            val retrofit = Retrofit.Builder()
                .baseUrl("https://download.jappstore.com/apps/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient!!)
                .build()

            retrofit.create(UpgradeAPI::class.java).downloadApk(downloadUrl!!)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                val body = response.body()
                                body?.let {

                                    val apkPathDir = if (UpgradeUtils.isExternalStorageWritable()) {
                                        Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_DOWNLOADS
                                        ).absolutePath
                                    } else {
                                        activity.filesDir.absolutePath
                                    }

                                    apkFile = File(apkPathDir, apkName)

                                    //输出流
                                    var sinkOut: Sink = Okio.sink(apkFile)

                                    //输入流
                                    var source: Source = Okio.source(it.byteStream())
                                    val totalSize = it.contentLength()
                                    //写入到本地存储空间
                                    var bufferSink: BufferedSink = Okio.buffer(sinkOut)

                                    bufferSink.writeAll(object : ForwardingSource(source) {

                                        var sum: Long = 0
                                        var oldRate = 0

                                        override fun read(sink: Buffer, byteCount: Long): Long {
                                            val readSize = super.read(sink, byteCount)
                                            if (readSize != -1L) {
                                                sum += readSize
                                                val rate =
                                                    Math.round(sum * 1f / totalSize * 100f).toInt()
                                                if (oldRate != rate) {
                                                    callback?.inProgress(rate * 1f / 100, totalSize)
                                                    oldRate = rate
                                                }
                                            }
                                            return readSize
                                        }
                                    })

                                    //刷新数据
                                    bufferSink.flush()

                                    sinkOut.close()

                                    source.close()

                                    //文件下载成功
                                    callback?.onDownloadFinished(apkFile!!)
                                }
                            }
                        } else {
                            callback?.onDownloadFailed("网络请求失败！")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        callback?.onDownloadFailed(t.message)
                    }
                })
            return true

        } else {
            callback?.onDownloadFailed("下载地址为空")
            return false
        }
    }

    /**
     * 展示弹窗
     */
    override fun showUpgradeDialog(entity: UpdateEntity?) {
        if (entity != null) {
            val fragmentManager = activity.supportFragmentManager
            UpdateDialogFragment
                .newInstance(entity)
                .showUpgradeDialog(manager = fragmentManager, provider = this)
        }
    }

    override fun getNewApkFile(): File? {
        return apkFile
    }

    /**
     * 是否存在新版本
     */
    private fun hasNewVersion(@NonNull entity: UpdateEntity?): Boolean {
        if (entity != null) {
            val currVersion = UpgradeUtils.getAppVersionCode(activity)
            val newVersion = entity.version
            if (!TextUtils.isEmpty(newVersion) && currVersion != -1) {
                return newVersion!!.toInt() > currVersion
            }
        }
        return false
    }


}