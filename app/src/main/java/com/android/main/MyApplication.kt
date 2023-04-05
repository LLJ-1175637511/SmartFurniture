package com.android.main

import android.app.Application
import com.google.gson.Gson
import com.llj.baselib.IOTLib
import com.llj.baselib.bean.UserConfigBean
import com.llj.baselib.utils.LogUtils

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        IOTLib.initContext(applicationContext)
        IOTLib.getConfigJson().let {
            if (it.isNotEmpty()){
                IOTLib.updateConfig(IOTLib.getConfigBean())
            }
        }
        LogUtils.openLog()
    }
}