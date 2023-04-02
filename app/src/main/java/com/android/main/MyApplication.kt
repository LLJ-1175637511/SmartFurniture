package com.android.main

import android.app.Application
import com.llj.baselib.IOTLib
import com.llj.baselib.bean.UserConfigBean
import com.llj.baselib.utils.LogUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val bean = UserConfigBean(
            userId = "22241",
            appKey = "9cb78afe15",
            deviceId = "29150",
            clientId = "1253",
            clientSecret = "e504b3533c"
        )
        IOTLib.init(applicationContext,bean)
        LogUtils.openLog()
    }
}