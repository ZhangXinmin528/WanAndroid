package com.coding.zxm.upgrade

/**
 * Created by ZhangXinmin on 2021/05/17.
 * Copyright (c) 5/17/21 . All rights reserved.
 */
class UpgradeManager private constructor() {

    companion object {
        private var sInstance: UpgradeManager? = null

        @Synchronized
        fun getInstance(): UpgradeManager? {
            if (sInstance == null) {
                sInstance = UpgradeManager()
            }
            return sInstance
        }
    }

    fun checkUpgrade() {

    }
}