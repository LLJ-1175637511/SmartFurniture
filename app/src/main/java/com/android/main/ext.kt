package com.android.main

import com.google.gson.Gson
import com.llj.baselib.IOTLib
import com.llj.baselib.IOTLib.getUcb
import com.llj.baselib.bean.Const
import com.llj.baselib.bean.UserConfigBean
import com.llj.baselib.save

fun getUserId() = getUcb().userId
