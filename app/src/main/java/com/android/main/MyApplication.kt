package com.android.main

import android.app.Application
import com.llj.baselib.IOTLib
import com.llj.baselib.bean.UserConfigBean

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val bean = UserConfigBean(
            userId = "19193",
            appKey = "a45ae9d745",
            deviceId = "25842",
            clientId = "1203",
            clientSecret = "561865303b"
        )
        IOTLib.init(applicationContext,bean)
    }
}