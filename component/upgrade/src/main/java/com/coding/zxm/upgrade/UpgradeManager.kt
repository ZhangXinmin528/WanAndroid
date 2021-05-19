package com.coding.zxm.upgrade

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager


/**
 * Created by ZhangXinmin on 2021/05/17.
 * Copyright (c) 5/17/21 . All rights reserved.
 * 应用更新
 */
class UpgradeManager private constructor(val builder: UpgradeBuilder) {

    companion object {
        private const val TAG = "UpgradeManager"

        private var mUpgradeLivedata: MutableLiveData<UpdateEntity> = MutableLiveData()

        private var mApkName: String = ""
    }

    /**
     * 检测版本更新
     * @param token
     */
    fun checkUpgrade() {
        val trustAllCert = object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf<X509Certificate>()
            }

        }

        val sslSocketFactory = SSLSocketFactoryCompat(trustAllCert)

        val loggingInterceptor =
            HttpLoggingInterceptor { message -> Log.d(TAG, "checkUpgrade()..msg:$message") }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .sslSocketFactory(sslSocketFactory, trustAllCert)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.bq04.com/apps/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(UpgradeAPI::class.java).checkUpdate(builder.upgradeToken)
            .enqueue(object : Callback<UpdateEntity> {
                override fun onFailure(call: Call<UpdateEntity>, t: Throwable) {
                    mUpgradeLivedata.postValue(null)
                    //TODO:检查更新失败
                }

                override fun onResponse(
                    call: Call<UpdateEntity>,
                    response: Response<UpdateEntity>
                ) {
                    if (response.isSuccessful) {
                        val entity = response.body()
                        mUpgradeLivedata.postValue(entity)
                        if (hasNewVersion(entity)) {
                            mApkName =
                                if (TextUtils.isEmpty(entity?.name)) builder.activity.applicationInfo.name else entity?.name!!

                            showUpgradeDialog(entity)
//                            downloadApk(entity?.installUrl)
                        } else {
                            Toast.makeText(builder.activity, "已是最新版本", Toast.LENGTH_SHORT).show()
                            //TODO：没有新版本
                        }
                    } else {
                        //TODO:检查更新失败
                        mUpgradeLivedata.postValue(null)
                    }
                }

            })

    }

    /**
     * 更新提示Dialog
     */
    private fun showUpgradeDialog(entity: UpdateEntity?) {
        if (entity != null) {
            val fragmentManager = builder.activity.supportFragmentManager
            UpdateDialogFragment.newInstance(entity)
                .show(fragmentManager, "dialog")
        }

    }

    /**
     * 下载新版本Apk
     */
    fun downloadApk(downloadUrl: String?) {
        if (!TextUtils.isDigitsOnly(downloadUrl)) {
            val trustAllCert = object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<out X509Certificate>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf<X509Certificate>()
                }

            }

            val sslSocketFactory = SSLSocketFactoryCompat(trustAllCert)

            val loggingInterceptor =
                HttpLoggingInterceptor { message -> Log.d(TAG, "checkUpgrade()..msg:$message") }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslSocketFactory, trustAllCert)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://download.jappstore.com/apps/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
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

                                    var inputStream: InputStream? = null
                                    var fileOutputStream: FileOutputStream? = null
                                    var bufferedInputStream: BufferedInputStream? = null

                                    var apkFile: File? = null
                                    val apkPathDir: String

                                    try {
                                        inputStream = it.byteStream()

                                        apkPathDir = if (isExternalStorageWritable()) {
                                            Environment.getExternalStoragePublicDirectory(
                                                Environment.DIRECTORY_DOWNLOADS
                                            ).absolutePath
                                        } else {
                                            builder.activity.filesDir.absolutePath
                                        }

                                        apkFile = File(apkPathDir, "$mApkName.apk")

                                        fileOutputStream = FileOutputStream(apkFile)
                                        bufferedInputStream = BufferedInputStream(inputStream)

                                        bufferedInputStream.available()
                                        val byte = ByteArray(1024)

                                        var length: Int

                                        while (bufferedInputStream.read(byte)
                                                .also { l -> length = l } != -1
                                        ) {
                                            fileOutputStream.write(byte, 0, length)
                                            fileOutputStream.flush()
                                        }

                                    } catch (e: IOException) {
                                        e.printStackTrace()
                                    } finally {
                                        try {
                                            fileOutputStream?.close()
                                            bufferedInputStream?.close()
                                            inputStream?.close()

                                            //TODO:进行安装
                                            apkFile?.let {
                                                installNewApkWithNoRoot(builder.activity, it)
                                            }

                                        } catch (e: IOException) {
                                            e.printStackTrace()
                                        }
                                    }
                                }
                            }
                        } else {
                            //TODO:下载失败
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        //TODO:下载失败
                    }
                })

        } else {
            //TODO:下载失败
        }
    }

    /**
     * 是否存在新版本
     */
    private fun hasNewVersion(@NonNull entity: UpdateEntity?): Boolean {
        if (entity != null) {
            val currVersion = getAppVersionCode(builder.activity)
            val newVersion = entity.version
            if (!TextUtils.isEmpty(newVersion) && currVersion != -1) {
                return newVersion!!.toInt() > currVersion
            }
        }
        return false
    }

    /**
     * install new version apk
     *
     * @param file
     */
    private fun installNewApkWithNoRoot(context: Context, file: File) {
        if (file.exists()) {
            Toast.makeText(context, "正在安装更新...", Toast.LENGTH_SHORT).show()
            val uri: Uri = Uri.fromFile(file)
            val intent = Intent(Intent.ACTION_VIEW)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                val contentUri: Uri = FileProvider
                    .getUriForFile(
                        context,
                        context.applicationContext.packageName + ".fileProvider",
                        file
                    )
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive")
            } else {
                intent.setDataAndType(uri, "application/vnd.android.package-archive")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        }
    }

    /**
     * Get app version code
     */
    private fun getAppVersionCode(context: Context): Int {
        val packageManager = context.packageManager
        if (packageManager != null) {
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        }
        return -1
    }


    /**
     * Checks if external storage is available for read and write
     */
    fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    class UpgradeBuilder {
        lateinit var activity: FragmentActivity
        lateinit var upgradeToken: String

        fun setActivity(activity: FragmentActivity): UpgradeBuilder {
            this.activity = activity
            return this
        }

        fun setUpgradeToken(token: String): UpgradeBuilder {
            this.upgradeToken = token
            return this
        }

        fun build(): UpgradeManager {
            if (activity == null || TextUtils.isEmpty(upgradeToken)) {
                throw NullPointerException("关键参数为空！")
            }
            return UpgradeManager(this)
        }
    }
}