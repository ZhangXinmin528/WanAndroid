package com.coding.zxm.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * Created by ZhangXinmin on 2022/04/29.
 * Copyright (c) 2022/4/29 . All rights reserved.
 * 页面销毁可在onCleared（）方法释放资源
 */
open class BaseViewModel(app:Application): AndroidViewModel(app) {

}