package com.coding.zxm.wanandroid.setting.model

import java.io.Serializable

/**
 * Created by ZhangXinmin on 2020/11/29.
 * Copyright (c) 2020 . All rights reserved.
 */
class LanguageEntity : Serializable {
    //语言名
    var name: String? = ""

    //简化
    var language: String = ""

    var checked: Boolean = false

    constructor(name: String?, language: String, checked: Boolean) {
        this.name = name
        this.language = language
        this.checked = checked
    }
}