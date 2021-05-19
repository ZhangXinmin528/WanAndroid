package com.coding.zxm.upgrade

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.coding.zxm.upgrade.utils.ColorUtil
import com.coding.zxm.upgrade.utils.DrawableUtil
import com.coding.zxm.upgrade.widget.NumberProgressBar
import java.util.*

/**
 * Created by ZhangXinmin on 2021/05/19.
 * Copyright (c) 5/19/21 . All rights reserved.
 * 应用更新Dialog
 * TODO:1.强制更新功能；2.忽略此版本；
 */
class UpdateDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var mEntity: UpdateEntity

    private var mContentTextView: TextView? = null
    private var mUpdateOkButton: Button? = null
    private var mNumberProgressBar: NumberProgressBar? = null
    private var mIvClose: ImageView? = null
    private var mTitleTextView: TextView? = null

    private var mLlClose: LinearLayout? = null

    //默认色
    private val mDefaultColor = -0x16bcc7
    private val mDefaultPicResId = R.mipmap.lib_update_app_top_bg
    private var mTopIv: ImageView? = null
    private var mIgnore: TextView? = null

    companion object {
        private const val PARAMS_UPDATE_INFO = "params_update_info"

        fun newInstance(@NonNull entity: UpdateEntity): UpdateDialogFragment {
            val fragment = UpdateDialogFragment()
            val args = Bundle()
            args.putSerializable(PARAMS_UPDATE_INFO, entity)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme_Upgrade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_update_app_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {

        //提示内容
        mContentTextView = view.findViewById(R.id.tv_update_info)
        //标题
        mTitleTextView = view.findViewById(R.id.tv_title)
        //更新按钮
        mUpdateOkButton = view.findViewById(R.id.btn_ok)
        mUpdateOkButton?.setOnClickListener(this)

        //进度条
        mNumberProgressBar = view.findViewById(R.id.npb)
        //关闭按钮
        mIvClose = view.findViewById(R.id.iv_close)
        mIvClose?.setOnClickListener(this)

        //关闭按钮+线 的整个布局
        mLlClose = view.findViewById<LinearLayout>(R.id.ll_close)
        //顶部图片
        mTopIv = view.findViewById<ImageView>(R.id.iv_top)
        //忽略
        mIgnore = view.findViewById<TextView>(R.id.tv_ignore)
        mIgnore?.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()

        dialog?.setCanceledOnTouchOutside(false)

        val window = dialog?.window
        window?.setGravity(Gravity.CENTER)
        val displayMetrics = context?.resources?.displayMetrics
        displayMetrics?.let {
            val lp = window?.attributes
            lp?.height = (it.heightPixels * 0.8f).toInt()
            window?.attributes = lp
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    private fun initData() {
        arguments?.let {
            if (it.containsKey(PARAMS_UPDATE_INFO)) {
                mEntity = it.getSerializable(PARAMS_UPDATE_INFO) as UpdateEntity
            }
        }

        initTheme()

        if (mEntity != null) {
            //弹出对话框
            val newVersion = mEntity.versionShort
            val targetSize = byte2FitMemorySize(mEntity.binary!!.fsize)
            val updateLog = mEntity.changelog

            var msg: String? = ""

            if (!TextUtils.isEmpty(targetSize)) {
                msg = "新版本大小：$targetSize\n\n"
            }

            if (!TextUtils.isEmpty(updateLog)) {
                msg += updateLog
            }

            //更新内容

            //更新内容
            mContentTextView!!.text = msg
            //标题
            //标题
            mTitleTextView!!.text = String.format("是否升级到%s版本？", newVersion)

            //TODO:强制更新
//            if (mUpdateApp.isConstraint()) {
//                mLlClose!!.visibility = View.GONE
//            } else {
//                //不是强制更新时，才生效
//                if (mUpdateApp.isShowIgnoreVersion()) {
//                    mIgnore!!.visibility = View.VISIBLE
//                }
//            }

        }
    }

    /**
     * 格式化文件大小
     */
    private fun byte2FitMemorySize(byteNum: Long): String? {
        return if (byteNum < 0) {
            "shouldn't be less than zero!"
        } else if (byteNum < 1024) {
            String.format(
                Locale.getDefault(),
                "%.3fB",
                byteNum.toDouble()
            )
        } else if (byteNum < 1048576) {
            String.format(
                Locale.getDefault(),
                "%.3fKB",
                byteNum.toDouble() / 1024
            )
        } else if (byteNum < 1073741824) {
            String.format(
                Locale.getDefault(),
                "%.3fMB",
                byteNum.toDouble() / 1048576
            )
        } else {
            String.format(
                Locale.getDefault(),
                "%.3fGB",
                byteNum.toDouble() / 1073741824
            )
        }
    }

    /**
     * 初始化主题风格
     */
    private fun initTheme() {
        val color = -1
        val topResId = -1
        if (-1 == topResId) {
            if (-1 == color) {
                //默认红色
                setDialogTheme(mDefaultColor, mDefaultPicResId)
            } else {
                setDialogTheme(color, mDefaultPicResId)
            }
        } else {
            if (-1 == color) {
                setDialogTheme(mDefaultColor, topResId)
            } else {
                //更加指定的上色
                setDialogTheme(color, topResId)
            }
        }
    }

    /**
     * 设置
     *
     * @param color    主色
     * @param topResId 图片
     */
    private fun setDialogTheme(color: Int, topResId: Int) {
        mTopIv!!.setImageResource(topResId)
        mUpdateOkButton!!.setBackgroundDrawable(
            DrawableUtil.getDrawable(dip2px(4, context!!), color)
        )
        mNumberProgressBar!!.setProgressTextColor(color)
        mNumberProgressBar!!.reachedBarColor = color
        //随背景颜色变化
        mUpdateOkButton!!.setTextColor(if (ColorUtil.isTextColorDark(color)) Color.BLACK else Color.WHITE)
    }

    private fun dip2px(dip: Int, context: Context): Int {
        return (dip * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_ok -> {
                if (ActivityCompat.checkSelfPermission(
                        context!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        Toast.makeText(
                            context,
                            getString(R.string.all_upgrade_tips_need_permissions),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            1001
                        )
                    }

                } else {

                }
            }
            R.id.iv_close -> {
                dismiss()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001) {
            if (grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //TODO:升级App

            } else {
                Toast.makeText(
                    context,
                    getString(R.string.all_upgrade_tips_need_permissions),
                    Toast.LENGTH_SHORT
                ).show()
                dismiss()
            }
        }
    }

}