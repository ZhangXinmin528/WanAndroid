package com.coding.zxm.upgrade.callback

/**
 * Created by ZhangXinmin on 2021/05/18.
 * Copyright (c) 5/18/21 . All rights reserved.
 */
interface UpgradeCallback {
    /**
     * 检查更新失败
     */
    fun onCheckUpgradeFailure(msg: String)
}